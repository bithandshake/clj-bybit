
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
  ; {:category (string)
  ;   "inverse", "linear"
  ;  :interval (string)
  ;   "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;  :limit (integer)
  ;   Max: 200
  ;  :start (ms)
  ;  :symbol (string)}
  ;
  ; @return (string)
  [{:keys [category start interval limit symbol]}]
  (let [query-start    start
        query-duration (kline.list.utils/query-duration interval limit)
        query-end      (+ query-start query-duration)]
       (str "category="  category
            "&symbol="   symbol
            "&interval=" interval
            "&limit="    limit
            "&start="    query-start
            "&end="      query-end)))

(defn kline-list-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:category (string)
  ;   "inverse", "linear"
  ;  :interval (string)
  ;   "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;  :limit (integer)
  ;   Max: 200
  ;  :start (ms)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :start 1644422400000 :symbol "ETHUSDT"})
  ; =>
  ; "https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :start 1644422400000 :symbol "ETHUSDT" :use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (kline-list-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/derivatives/v3/public/kline?" query-string)))

(defn kline-list-uri-list
  ; @ignore
  ;
  ; @description
  ; The maximum kline limit in Bybit.com queries is 200. This function generates
  ; a URI list for multiple queries to make possible to query more than 200 kline at a time.
  ;
  ; @param (map) uri-props
  ; {:category (string)
  ;   "inverse", "linear"
  ;  :interval (string)
  ;   "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;  :limit (integer)
  ;  :start (ms)(opt)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (kline-list-uri-list {:interval "1" :limit 240 :symbol "ETHUSDT"})
  ; =>
  ; ["https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=40&start=1646401800000&end=..."
  ;  "https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=200&start=1646404200000&end=..."]
  ;
  ; @return (strings in vector)
  [{:keys [interval limit start] :as uri-props}]
  ; This function generates a URI list for querying klines.
  ; - If you don't pass the 'start' value the time range starts at (present - duration)
  ;   and ends in the present.
  ; - If you pass the 'start' value the time range starts at the given value
  ;   and ends at (start + duration).
  (let [now                 (time/epoch-ms)
        query-list-duration (kline.list.utils/query-duration interval limit)
        query-list-start    (or start (- now   query-list-duration))
        query-list-end      (if start (+ start query-list-duration) now)]

       ; In every iteration ...
       ; ... the URI list expands with a URI.
       ; ... the limit decreases with 200.
       ; ... the offset increase with a duration of 200 interval.
       (letfn [(f [uri-list limit offset]
                  (if (> limit 200)

                      ; If the limit is greater than 200 ...
                      (let [query-duration (kline.list.utils/query-duration interval 200)
                            query-start    (+ query-list-start offset)
                            uri-props      (merge uri-props {:limit 200 :start query-start})]
                           (f (conj uri-list (kline-list-uri uri-props))
                              (- limit 200)
                              (+ offset query-duration)))

                      ; If the limit is NOT greater than 200 ...
                      (let [query-duration (kline.list.utils/query-duration interval limit)
                            query-start    (+ query-list-start offset)
                            uri-props      (merge uri-props {:limit limit :start query-start})]
                           (conj uri-list (kline-list-uri uri-props)))))]

              ; ...
              (f [] limit 0))))
