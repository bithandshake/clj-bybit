
(ns bybit.position.list.headers
    (:require [bybit.core.request.headers :as core.request.headers]
              [bybit.position.list.uri    :as position.list.uri]
              [time.api                   :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-list-param-string
  ; @param (map) param-props
  ; {:api-key (string)
  ;  :base-coin (string)(opt)
  ;   Default: "BTC"
  ;   W/ {:category "option"}
  ;  :category (string)
  ;   "linear", "option"
  ;  :cursor (string)(opt)
  ;  :direction (string)(opt)
  ;   "next", "prev"
  ;  :limit (number)(opt)
  ;   Default: 20
  ;   Max: 50
  ;  :symbol (string)(opt)}
  ; @param (string) timestamp
  ;
  ; @example
  ; (position-list-param-string {:base-coin "BTC"} "1645550000123")
  ; =>
  ; "1645550000123XXXXXXXXXX5000baseCoin=BTC"
  ;
  ; @return (string)
  [{:keys [api-key] :as param-props} timestamp]
  (let [query-string (position.list.uri/position-list-query-string param-props)]
       (str timestamp api-key 5000 query-string)))

(defn position-list-headers
  ; @param (map) uri-props
  ; {:base-coin (string)(opt)
  ;   Default: "BTC"
  ;   W/ {:category "option"}
  ;  :category (string)
  ;   "linear", "option"
  ;  :cursor (string)(opt)
  ;  :direction (string)(opt)
  ;   "next", "prev"
  ;  :limit (number)(opt)
  ;   Default: 20
  ;   Max: 50
  ;  :symbol (string)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (position-list-headers {:category "linear" :symbol "BTC"})
  ;
  ; @usage
  ; (position-list-headers {:category "linear" :symbol "BTC" :use-mainnet? true})
  ;
  ; @return (map)
  ; {"X-BAPI-SIGN-TYPE" (integer)
  ;  "X-BAPI-SIGN" (string)
  ;  "X-BAPI-API-KEY" (string)
  ;  "X-BAPI-TIMESTAMP" (string)
  ;  "X-BAPI-RECV-WINDOW" (integer)}
  [headers-props]
  (let [timestamp    (time/epoch-ms)
        param-string (position-list-param-string headers-props timestamp)]
       (core.request.headers/GET-headers headers-props param-string timestamp)))
