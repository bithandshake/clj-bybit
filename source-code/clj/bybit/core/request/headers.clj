
(ns bybit.core.request.headers
    (:require [server-fruits.hash :as hash]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn raw-request-body-item
  ; @param (string) name
  ; @param (*) value
  ;
  ; @example
  ;  (raw-request-body-item "basePrice" 42)
  ;  =>
  ;  "\"basePrice\": \"42\""
  ;
  ; @return (string)
  [name value]
  (if value (str "\""name"\": \""value"\"")))

(defn raw-request-body
  ; @param (strings in vector) items
  ;
  ; @example
  ;  (raw-request-body ["\"basePrice\": \"42\"" "\"symbol\": \"ETHUSDT\""])
  ;  =>
  ;  "{\"basePrice\": \"42\", \"symbol\": \"ETHUSDT\"}"
  ;
  ; @return (string)
  [items]
  (letfn [(f [result dex item] (str (if-not (= dex 0) ", ") item))]
         (str "{"(reduce-kv f "" items))"}"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn GET-headers
  ; @param (map) headers-props
  ;  {:api-key (string)
  ;   :api-secret (string)}
  ; @param (string) timestamp
  ; @param (string) param-string
  ;
  ; @usage
  ;  (GET-headers {:api-key "..." :api-secret "..."} "..." "...")
  ;
  ; @return (map)
  ;  {"X-BAPI-SIGN-TYPE" (integer)
  ;   "X-BAPI-SIGN" (string)
  ;   "X-BAPI-API-KEY" (string)
  ;   "X-BAPI-TIMESTAMP" (string)
  ;   "X-BAPI-RECV-WINDOW" (integer)}
  [{:keys [api-key api-secret]} timestamp param-string]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [sign (hash/hmac-sha256 param-string api-secret)]
       {"X-BAPI-SIGN-TYPE"   2
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" 5000}))
