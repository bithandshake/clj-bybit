
(ns bybit.order.create.request
    (:require [bybit.core.response.errors  :as core.response.errors]
              [bybit.core.response.helpers :as core.response.helpers]
              [bybit.order.create.body     :as order.create.body]
              [bybit.order.create.headers  :as order.create.headers]
              [bybit.order.create.receive  :as order.create.receive]
              [bybit.order.create.uri      :as order.create.uri]
              [candy.api                   :refer [return]]
              [clj-http.client             :as clj-http.client]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-order-create!
  ; @param (map) request-props
  ; {:api-key (string)
  ;  :api-secret (string)
  ;  :base-price (?)(opt)
  ;  :category (string)
  ;   "linear", "option"
  ;  :close-on-trigger? (boolean)(opt)
  ;   Default: false
  ;  :implied-volatility (string)(opt)
  ;  :order-link-id (string)(opt)
  ;  :order-type (string)
  ;   "Limit", "Market"
  ;  :position-dex (string)(opt)
  ;  :price (?)(opt)
  ;  :protect-market-maker? (boolean)(opt)
  ;   Default: false
  ;  :quantity (USD)
  ;  :reduce-only? (boolean)(opt)
  ;   Default: false
  ;  :side (string)
  ;   "Buy", "Sell"
  ;  :sl-trigger-by (string)(opt)
  ;  :stop-loss (?)(opt)
  ;  :symbol (string)
  ;  :take-profit (?)(opt)
  ;  :time-in-force (string)
  ;   "FillOrKill", "GoodTillCancel", "ImmediateOrCancel", "PostOnly"
  ;  :tp-trigger-by (string)(opt)
  ;  :trigger-by (string)(opt)
  ;   "Market price", "Mark price"              <----      ez
  ;   "IndexPrice", "LastPrice", "MarkPrice"    <---- vagy ez
  ;  :trigger-price (?)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (request-order-create! {:api-key "..." :api-secret "..." :category "linear" :quantity 100 :symbol "ETHUSDT"})
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
        response-body (core.response.helpers/POST-response->body response)]
       response-body))


        ;response      (clj-http.client/post uri {:form-params form-params :as :x-www-form-urlencoded})
        ;response-body (core.response.helpers/POST-response->body response)]
       ;(if (core.response.errors/response-body->error? response-body)
        ;  (return response-body)
        ;  response-body]))
