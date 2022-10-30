
(ns bybit.position.list.headers
    (:require [bybit.position.list.uri :as position.list.uri]
              [server-fruits.hash      :as hash]
              [time.api                :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-list-param-string
  ; @param (map) param-props
  ;  {:api-key (string)
  ;   :base-coin (string)(opt)
  ;    Default: "BTC"
  ;    W/ {:category "option"}
  ;   :category (string)
  ;    "linear", "option"
  ;   :cursor (string)(opt)
  ;   :direction (string)(opt)
  ;    "next", "prev"
  ;   :limit (number)(opt)
  ;    Default: 20
  ;    Max: 50
  ;   :symbol (string)(opt)}
  ; @param (string) timestamp
  ;
  ; @return (string)
  [{:keys [api-key] :as param-props} timestamp]
  (let [query-string (position.list.uri/position-list-query-string param-props)]
       (str timestamp api-key 5000 query-string)))

(defn position-list-headers
  ; @param (map) uri-props
  ;  {:base-coin (string)(opt)
  ;    Default: "BTC"
  ;    W/ {:category "option"}
  ;   :category (string)
  ;    "linear", "option"
  ;   :cursor (string)(opt)
  ;   :direction (string)(opt)
  ;    "next", "prev"
  ;   :limit (number)(opt)
  ;    Default: 20
  ;    Max: 50
  ;   :symbol (string)(opt)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @usage
  ;  (position-list-headers {:category "linear" :symbol "BTC"})
  ;
  ; @usage
  ;  (position-list-headers {:category "linear" :symbol "BTC" :use-mainnet? true})
  ;
  ; @return (map)
  ;  {"X-BAPI-SIGN-TYPE" (integer)
  ;   "X-BAPI-SIGN" (string)
  ;   "X-BAPI-API-KEY" (string)
  ;   "X-BAPI-TIMESTAMP" (string)
  ;   "X-BAPI-RECV-WINDOW" (integer)}
  [{:keys [api-key api-secret use-mainnet?] :as headers-props}]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [timestamp    (time/epoch-ms)
        param-string (position-list-param-string headers-props timestamp)
        sign         (hash/hmac-sha256 param-string api-secret)]
       {"X-BAPI-SIGN-TYPE"   2
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" 5000}))
