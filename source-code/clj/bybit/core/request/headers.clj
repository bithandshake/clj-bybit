
(ns bybit.core.request.headers
    (:require [server-fruits.hash :as hash]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn GET-headers
  ; @param (map) headers-props
  ;  {:api-key (string)
  ;   :api-secret (string)}
  ; @param (string) param-string
  ; @param (string) timestamp
  ;
  ; @usage
  ;  (GET-headers {:api-key "..." :api-secret "..."} "..." "...")
  ;
  ; @return (map)
  ;  {"X-BAPI-API-KEY" (string)
  ;   "X-BAPI-RECV-WINDOW" (integer)
  ;   "X-BAPI-SIGN" (string)
  ;   "X-BAPI-SIGN-TYPE" (integer)
  ;   "X-BAPI-TIMESTAMP" (string)}
  [{:keys [api-key api-secret]} param-string timestamp]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [sign (hash/hmac-sha256 param-string api-secret)]
       {"X-BAPI-SIGN-TYPE"   "2"
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" "5000"}))

(defn POST-headers
  ; @param (map) headers-props
  ;  {:api-key (string)
  ;   :api-secret (string)}
  ; @param (string) param-string
  ; @param (string) timestamp
  ;
  ; @usage
  ;  (POST-headers {:api-key "..." :api-secret "..."} "..." "...")
  ;
  ; @return (map)
  ;  {"Content-Type" (string)
  ;   "X-BAPI-API-KEY" (string)
  ;   "X-BAPI-RECV-WINDOW" (integer)
  ;   "X-BAPI-SIGN" (string)
  ;   "X-BAPI-SIGN-TYPE" (integer)
  ;   "X-BAPI-TIMESTAMP" (string)}
  [{:keys [api-key api-secret]} param-string timestamp]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [sign (hash/hmac-sha256 param-string api-secret)]
       {"X-BAPI-SIGN-TYPE"   "2"
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" "5000"
        "Content-Type"       "application/json"}))
