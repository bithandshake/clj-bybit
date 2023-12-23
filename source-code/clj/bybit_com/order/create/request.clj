
(ns bybit-com.order.create.request
    (:require [bybit-com.core.response.errors :as core.response.errors]
              [bybit-com.core.response.utils  :as core.response.utils]
              [bybit-com.order.create.body    :as order.create.body]
              [bybit-com.order.create.headers :as order.create.headers]
              [bybit-com.order.create.uri     :as order.create.uri]
              [clj-http.client                :as clj-http.client]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-order-create!
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Sends a market order to the bybit.com API.
  ;
  ; @param (map) request-props
  ; {:api-key (string)
  ;  :api-secret (string)
  ;  :category (integer)(opt)
  ;   0 (normal), 1 (TP/SL)
  ;   Default: 0
  ;  :order-link-id (string)(opt)
  ;  :order-price (?)(opt)
  ;   When the order type is "Market", the order price is optional.
  ;  :order-type (string)
  ;   "Limit", "Limit_maker", "Market"
  ;  :quantity (?)
  ;  :side (string)
  ;   "Buy", "Sell"
  ;  :smp-type (string)(opt)
  ;   "None", "CancelMaker", "CancelTaker", "CancelBoth"
  ;   Default: "None"
  ;  :symbol (string)
  ;  :time-in-force (string)(opt)
  ;   "GTC" (Good Till Cancel), "FOK" (Fill Or Kill), "IOC" (Immediate Or Cancel)
  ;   Default: "GTC"
  ;  :trigger-price (?)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (request-order-create! {:api-key "..." :api-secret "..." :side "Buy" :quantity 100 :symbol "ETHUSDT"})
  ; =>
  ; {}
  ;
  ; @return (map)
  ; {}
  [{:keys [use-mainnet?] :as request-props}]
  (let [uri           (order.create.uri/order-create-uri               request-props)
        headers       (order.create.headers/order-create-headers       request-props)
        body          (order.create.body/order-create-raw-request-body request-props)
        response      (clj-http.client/post uri {:body body :headers headers})
        response-body (core.response.utils/POST-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (-> response-body)
           (-> response-body))))
