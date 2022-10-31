
(ns bybit.order.create.headers
    (:require [bybit.core.request.headers :as core.request.headers]
              [bybit.order.create.uri     :as order.create.uri]
              [server-fruits.hash         :as hash]
              [time.api                   :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-raw-request-body
  ; @param (map) body-props
  ;
  ; @return (string)
  [body-props]
  (let [items [(core.request.headers/raw-request-body-item "basePrice"      (:base-price            body-props))
               (core.request.headers/raw-request-body-item "category"       (:category              body-props))
               (core.request.headers/raw-request-body-item "closeOnTrigger" (:close-on-trigger?     body-props))
               (core.request.headers/raw-request-body-item "iv"             (:implied-volatility    body-props))
               (core.request.headers/raw-request-body-item "orderLinkId"    (:order-link-id         body-props))
               (core.request.headers/raw-request-body-item "orderType"      (:order-type            body-props))
               (core.request.headers/raw-request-body-item "positionIdx"    (:position-dex          body-props))
               (core.request.headers/raw-request-body-item "price"          (:price                 body-props))
               (core.request.headers/raw-request-body-item "mmp"            (:protect-market-maker? body-props))
               (core.request.headers/raw-request-body-item "qty"            (:quantity              body-props))
               (core.request.headers/raw-request-body-item "reduceOnly"     (:reduce-only?          body-props))
               (core.request.headers/raw-request-body-item "side"           (:side                  body-props))
               (core.request.headers/raw-request-body-item "slTriggerBy"    (:sl-trigger-by         body-props))
               (core.request.headers/raw-request-body-item "stopLoss"       (:stop-loss             body-props))
               (core.request.headers/raw-request-body-item "symbol"         (:symbol                body-props))
               (core.request.headers/raw-request-body-item "takeProfit"     (:take-profit           body-props))
               (core.request.headers/raw-request-body-item "timeInForce"    (:time-in-force         body-props))
               (core.request.headers/raw-request-body-item "tpTriggerBy"    (:tp-trigger-by         body-props))
               (core.request.headers/raw-request-body-item "triggerBy"      (:trigger-by            body-props))
               (core.request.headers/raw-request-body-item "triggerPrice"   (:trigger-price         body-props))]]
       (core.request.headers/raw-request-body items)))

(defn order-create-headers
  ; @param (map) uri-props
  ;  {}
  ;
  ; @usage
  ;  (order-create-headers {})
  ;
  ; @usage
  ;  (order-create-headers {:use-mainnet? true})
  ;
  ; @return (map)
  ;  {}
  [{:keys [] :as headers-props}])
