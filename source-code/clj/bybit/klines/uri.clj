
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.klines.uri
    (:require [bybit.klines.helpers :as klines.helpers]
              [bybit.uri.config     :as uri.config]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn kline-list-uri
  ; @param (map) uri-props
  ;  {:from (s)(opt)
  ;   :interval (string)
  ;    "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;   :limit (integer)
  ;    Max: 200
  ;   :symbol (string)
  ;    "BTCUSD", "ETHUSD"
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSD"})
  ;  =>
  ;  "https://api-testnet.bybit.com/v2/public/kline/list?symbol=ETHUSD&interval=1&limit=60&from=1646401800"
  ;
  ; @example
  ;  (bybit/kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSD" :use-mainnet? true})
  ;  =>
  ;  "https://api.bybit.com/v2/public/kline/list?symbol=ETHUSD&interval=1&limit=60&from=1646401800"
  ;
  ; @return (string)
  [{:keys [from interval limit symbol use-mainnet?]}]
  (let [query-from (or from (klines.helpers/query-from interval limit))
        address    (if use-mainnet? uri.config/PUBLIC-API-ADDRESS uri.config/PUBLIC-TEST-API-ADDRESS)]
       (str address "/kline/list"
                    "?symbol="   symbol
                    "&interval=" interval
                    "&limit="    limit
                    "&from="     query-from)))

(defn kline-list-uri-list
  ; @param (map) uri-props
  ;  {:from (s)(opt)
  ;   :interval (string)
  ;    "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;   :limit (integer)
  ;   :symbol (string)
  ;    "BTCUSD", "ETHUSD"
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/kline-list-uri-list {:interval "1" :limit 240 :symbol "ETHUSD"})
  ;  =>
  ;  ["https://api-testnet.bybit.com/v2/public/kline/list?symbol=ETHUSD&interval=1&limit=40&from=1646401800"
  ;   "https://api-testnet.bybit.com/v2/public/kline/list?symbol=ETHUSD&interval=1&limit=200&from=1646404200"]
  ;
  ; @return (strings in vector)
  [{:keys [interval] :as uri-props}]
  (letfn [(f [uri-list {:keys [limit] :as uri-props} lap]
             (if (> limit 200)
                 ; If limit is greater than 200 ...
                 (let [uri-props (merge uri-props {:limit 200 :from (klines.helpers/query-from interval (* lap 200))})]
                      (f (cons (kline-list-uri uri-props) uri-list)
                         (assoc uri-props :limit (- limit 200))
                         (inc lap)))
                 ; If limit is NOT greater than 200 ...
                 (let [uri-props (merge uri-props {:limit limit :from (klines.helpers/query-from interval (+ limit (* (dec lap) 200)))})]
                      (cons (kline-list-uri uri-props) uri-list))))]
         ; *
         (vec (f [] uri-props 1))))
