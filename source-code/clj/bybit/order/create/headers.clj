
(ns bybit.order.create.headers
    (:require [bybit.core.request.headers :as core.request.headers]
              [bybit.order.create.body    :as order.create.body]
              [time.api                   :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-param-string
  ; @param (map) param-props
  ; {:api-key (string)
  ;  ...}
  ; @param (string) timestamp
  ;
  ; @example
  ; (order-create-param-string {:base-price 42} "1645550000123")
  ; =>
  ; "1645550000123XXXXXXXXXX5000{\"basePrice\": \"42\"}"
  ;
  ; @return (string)
  [{:keys [api-key] :as param-props} timestamp]
  (let [raw-request-body (order.create.body/order-create-raw-request-body param-props)]
       (str timestamp api-key 5000 raw-request-body)))

(defn order-create-headers
  ; @param (map) uri-props
  ; {}
  ;
  ; @usage
  ; (order-create-headers {})
  ;
  ; @usage
  ; (order-create-headers {:use-mainnet? true})
  ;
  ; @return (map)
  ; {"Content-Type" (string)
  ;  "X-BAPI-SIGN-TYPE" (integer)
  ;  "X-BAPI-SIGN" (string)
  ;  "X-BAPI-API-KEY" (string)
  ;  "X-BAPI-TIMESTAMP" (string)
  ;  "X-BAPI-RECV-WINDOW" (integer)}
  [headers-props]
  (let [timestamp    (time/epoch-ms)
        param-string (order-create-param-string headers-props timestamp)]
       (core.request.headers/POST-headers headers-props param-string timestamp)))
