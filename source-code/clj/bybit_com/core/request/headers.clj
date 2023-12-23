
(ns bybit-com.core.request.headers
    (:require [buddy.core.codecs]
              [buddy.core.mac]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn GET-headers
  ; @ignore
  ;
  ; @param (map) headers-props
  ; {:api-key (string)
  ;  :api-secret (string)}
  ; @param (string) param-string
  ; @param (string) timestamp
  ;
  ; @usage
  ; (GET-headers {:api-key "..." :api-secret "..."} "..." "...")
  ;
  ; @return (map)
  ; {"X-BAPI-API-KEY" (string)
  ;  "X-BAPI-RECV-WINDOW" (integer)
  ;  "X-BAPI-SIGN" (string)
  ;  "X-BAPI-SIGN-TYPE" (integer)
  ;  "X-BAPI-TIMESTAMP" (string)}
  [{:keys [api-key api-secret]} param-string timestamp]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [sign (-> param-string str (buddy.core.mac/hash {:key api-secret :alg :hmac+sha256}) buddy.core.codecs/bytes->hex)]
       {"X-BAPI-SIGN-TYPE"   "2"
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" "5000"}))

(defn POST-headers
  ; @ignore
  ;
  ; @param (map) headers-props
  ; {:api-key (string)
  ;  :api-secret (string)}
  ; @param (string) param-string
  ; @param (string) timestamp
  ;
  ; @usage
  ; (POST-headers {:api-key "..." :api-secret "..."} "..." "...")
  ;
  ; @return (map)
  ; {"Content-Type" (string)
  ;  "X-BAPI-API-KEY" (string)
  ;  "X-BAPI-RECV-WINDOW" (integer)
  ;  "X-BAPI-SIGN" (string)
  ;  "X-BAPI-SIGN-TYPE" (integer)
  ;  "X-BAPI-TIMESTAMP" (string)}
  [{:keys [api-key api-secret]} param-string timestamp]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [sign (-> param-string str (buddy.core.mac/hash {:key api-secret :alg :hmac+sha256}) buddy.core.codecs/bytes->hex)]
       {"X-BAPI-SIGN-TYPE"   "2"
        "X-BAPI-SIGN"        sign
        "X-BAPI-API-KEY"     api-key
        "X-BAPI-TIMESTAMP"   timestamp
        "X-BAPI-RECV-WINDOW" "5000"
        "Content-Type"       "application/json"}))
