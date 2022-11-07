
(ns bybit.order.create.headers
    (:require [bybit.core.request.body :as core.request.body]
              [bybit.order.create.uri  :as order.create.uri]
              [server-fruits.hash      :as hash]
              [time.api                :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------


; ez nem inkabb egy body.clj fájlba valo?

(defn order-create-raw-request-body
  ; @param (map) body-props
  ;
  ; @return (string)
  [body-props]
  (let [items [["basePrice"      (:base-price            body-props)]
               ["category"       (:category              body-props)]
               ["closeOnTrigger" (:close-on-trigger?     body-props)]
               ["iv"             (:implied-volatility    body-props)]
               ["orderLinkId"    (:order-link-id         body-props)]
               ["orderType"      (:order-type            body-props)]
               ["positionIdx"    (:position-dex          body-props)]
               ["price"          (:price                 body-props)]
               ["mmp"            (:protect-market-maker? body-props)]
               ["qty"            (:quantity              body-props)]
               ["reduceOnly"     (:reduce-only?          body-props)]
               ["side"           (:side                  body-props)]
               ["slTriggerBy"    (:sl-trigger-by         body-props)]
               ["stopLoss"       (:stop-loss             body-props)]
               ["symbol"         (:symbol                body-props)]
               ["takeProfit"     (:take-profit           body-props)]
               ["timeInForce"    (:time-in-force         body-props)]
               ["tpTriggerBy"    (:tp-trigger-by         body-props)]
               ["triggerBy"      (:trigger-by            body-props)]
               ["triggerPrice"   (:trigger-price         body-props)]]]
       (core.request.body/raw-request-body items)))

[(defn order-create-headers
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
  [{:keys [] :as headers-props}])]
