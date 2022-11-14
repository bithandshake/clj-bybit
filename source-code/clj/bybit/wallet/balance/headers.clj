
(ns bybit.wallet.balance.headers
    (:require [bybit.core.request.headers :as core.request.headers]
              [bybit.wallet.balance.uri   :as wallet.balance.uri]
              [time.api                   :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wallet-balance-param-string
  ; @param (map) param-props
  ;  {:api-key (string)
  ;   :coin (string)(opt)}
  ; @param (string) timestamp
  ;
  ; @example
  ;  (wallet-balance-param-string {:coin "ETH"} "1645550000123")
  ;  =>
  ;  "1645550000123XXXXXXXXXX5000coin=ETH"
  ;
  ; @return (string)
  [{:keys [api-key] :as param-props} timestamp]
  (let [query-string (wallet.balance.uri/wallet-balance-query-string param-props)]
       (str timestamp api-key 5000 query-string)))

(defn wallet-balance-headers
  ; @param (map) uri-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :coin (string)(opt)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @usage
  ;  (wallet-balance-headers {})
  ;
  ; @usage
  ;  (wallet-balance-headers {:use-mainnet? true})
  ;
  ; @return (map)
  ;  {"X-BAPI-SIGN-TYPE" (integer)
  ;   "X-BAPI-SIGN" (string)
  ;   "X-BAPI-API-KEY" (string)
  ;   "X-BAPI-TIMESTAMP" (string)
  ;   "X-BAPI-RECV-WINDOW" (integer)}
  [headers-props]
  (let [timestamp    (time/epoch-ms)
        param-string (wallet-balance-param-string headers-props timestamp)]
       (core.request.headers/GET-headers headers-props param-string timestamp)))
