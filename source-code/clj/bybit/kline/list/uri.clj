
(ns bybit.kline.list.uri
    (:require [bybit.core.uri.config    :as core.uri.config]
              [bybit.kline.list.helpers :as kline.list.helpers]
              [time.api                 :as time]))

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
  ;  :start (ms)(opt)
  ;  :symbol (string)}
  ;
  ; @return (string)
  [{:keys [category start interval limit symbol]}]
  (let [query-start    (or start (kline.list.helpers/query-start interval limit))
        query-duration (kline.list.helpers/query-duration        interval limit)
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
  ;  :start (ms)(opt)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSDT"})
  ; =>
  ; "https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
  ;
  ; @example
  ; (kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSDT" :use-mainnet? true})
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
  [{:keys [interval] :as uri-props}]
  (letfn [(f [uri-list {:keys [limit] :as uri-props} lap]
             (if (> limit 200)
                 ; If limit is greater than 200 ...
                 (let [uri-props (merge uri-props {:limit 200 :start (kline.list.helpers/query-start interval (* lap 200))})]
                      (f (cons (kline-list-uri uri-props) uri-list)
                         (assoc uri-props :limit (- limit 200))
                         (inc lap)))
                 ; If limit is NOT greater than 200 ...
                 (let [uri-props (merge uri-props {:limit limit :start (kline.list.helpers/query-start interval (+ limit (* (dec lap) 200)))})]
                      (cons (kline-list-uri uri-props) uri-list))))]
         ; *
         (vec (f [] uri-props 1))))
