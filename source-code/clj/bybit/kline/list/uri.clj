
(ns bybit.kline.list.uri
    (:require [bybit.core.uri.config  :as core.uri.config]
              [bybit.kline.list.utils :as kline.list.utils]
              [time.api               :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn kline-list-query-string
  ; @ignore
  ;
  ; @param (map) query-props
  ; {:interval (string)
  ;   "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;  :limit (integer)
  ;   Max: 1000
  ;  :start-ms (ms)
  ;  :symbol (string)}
  ;
  ; @return (string)
  [{:keys [start-ms interval limit symbol] :or {limit 1000}}]
  ; E.g. start: "2022-04-20T04:20:42.000Z"
  ;      limit: 5
  ;      interval: "1m"
  ;      =>
  ;      query-start-ms: 1650428442000
  ;      query-end-ms:   1650428682000 (+ 4 min not 5!)
  ;      =>
  ;      first kline starts: "2022-04-20T04:20:00.000Z" (04:20:00 -> 04:21:00)
  ;      last kline starts:  "2022-04-20T04:24:00.000Z" (04:24:00 -> 04:25:00)
  (let [query-start-ms       start-ms
        query-duration-ms    (kline.list.utils/query-duration-ms interval limit)
        interval-duration-ms (kline.list.utils/interval-duration-ms interval)
        query-end-ms         (- (+ query-start-ms query-duration-ms) interval-duration-ms)]
       (str "&symbol="    symbol
            "&interval="  interval
            "&limit="     limit
            "&startTime=" query-start-ms
            "&endTime="   query-end-ms)))

(defn kline-list-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:interval (string)
  ;   "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;  :limit (integer)
  ;   Max: 1000
  ;  :start-ms (ms)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :start 1644422400000 :symbol "ETHUSDT"})
  ; =>
  ; "https://api-testnet.bybit.com/spot/v3/public/quote/kline?symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :start 1644422400000 :symbol "ETHUSDT" :use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/spot/v3/public/quote/kline?symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (kline-list-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/spot/v3/public/quote/kline?" query-string)))

(defn kline-list-uri-list
  ; @ignore
  ;
  ; @description
  ; The maximum kline limit in Bybit.com queries is 1000.
  ; This function generates a URI list for multiple queries to make possible to
  ; query more than 1000 kline at a time.
  ;
  ; @param (map) uri-props
  ; {:interval (string)
  ;   "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;  :limit (integer)
  ;  :start (string)(opt)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (kline-list-uri-list {:interval "1" :limit 1040 :symbol "ETHUSDT"})
  ; =>
  ; {:generated-at 1682949274487
  ;  :uri-list ["https://api-testnet.bybit.com/spot/v3/public/quote/kline?&symbol=ETHUSDT&interval=1&limit=40&startTime=..."
  ;             "https://api-testnet.bybit.com/spot/v3/public/quote/kline?&symbol=ETHUSDT&interval=1&limit=1000&startTime=..."]}
  ;
  ; @return (map)
  ; {:generated-at (ms)
  ;  :uri-list (strings in vector)}
  [{:keys [interval limit start] :as uri-props :or {limit 1000}}]
  ; This function generates a URI list for querying klines.
  ; - If you don't pass the 'start' value the time range starts at (present - duration)
  ;   and ends in the present.
  ; - If you pass the 'start' value the time range starts at the given value
  ;   and ends at (start + duration).
  (let [time-now               (time/epoch-ms)
        query-list-duration-ms (kline.list.utils/query-duration-ms interval limit)
        query-list-start-ms    (if start (time/timestamp-string->epoch-ms start)
                                         (- time-now query-list-duration-ms))]

       ; In every iteration ...
       ; ... the URI list expands with a URI.
       ; ... the limit decreases with 1000.
       ; ... the offset increase with a duration of 1000 interval.
       (letfn [(f [uri-list limit offset-ms]
                  (if (> limit 1000)

                      ; If the limit is greater than 1000 ...
                      (let [query-duration-ms (kline.list.utils/query-duration-ms interval 1000)
                            query-start-ms    (+ query-list-start-ms offset-ms)
                            uri-props         (merge uri-props {:limit 1000 :start-ms query-start-ms})]
                           (f (conj uri-list (kline-list-uri uri-props))
                              (- limit 1000)
                              (+ offset-ms query-duration-ms)))

                      ; If the limit is NOT greater than 1000 ...
                      (let [query-duration-ms (kline.list.utils/query-duration-ms interval limit)
                            query-start-ms    (+ query-list-start-ms offset-ms)
                            uri-props      (merge uri-props {:limit limit :start-ms query-start-ms})]
                           (conj uri-list (kline-list-uri uri-props)))))]

              ; ...
              {:generated-at time-now
               :uri-list (f [] limit 0)})))
