
(ns bybit.order.create.body
    (:require [bybit.core.request.body :as core.request.body]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-raw-request-body
  ; @ignore
  ;
  ; @param (map) body-props
  ; {}
  ;
  ; @example
  ; (order-create-raw-request-body {:order-price 42})
  ; =>
  ; "..."
  ; "{\"orderPrice\": \"42\"}"
  ;
  ; @return (string)
  [body-props]
  (let [items [["orderCategory" (:category      body-props)]
               ["orderLinkId"   (:order-link-id body-props)]
               ["orderPrice"    (:order-price   body-props)]
               ["orderQty"      (:quantity      body-props)]
               ["orderType"     (:order-type    body-props)]
               ["side"          (:side          body-props)]
               ["smpType"]      (:smp-type      body-props)
               ["symbol"        (:symbol        body-props)]
               ["timeInForce"   (:time-in-force body-props)]
               ["triggerPrice"  (:trigger-price body-props)]]]
       (core.request.body/raw-request-body items)))
