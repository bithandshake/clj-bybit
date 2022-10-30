
(ns bybit.order.create.request
    (:require [bybit.core.request.sign     :as core.request.sign]
              [bybit.core.response.errors  :as core.response.errors]
              [bybit.core.response.helpers :as core.response.helpers]
              [bybit.order.create.uri      :as order.create.uri]
              [clj-http.client             :as clj-http.client]
              [mid-fruits.candy            :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-order-create!
  ; @param (map) request-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :base-price (?)(opt)
  ;   :category (string)
  ;    "linear", "option"
  ;   :order-type (string)
  ;    "Limit", "Market"
  ;   :price (?)(opt)
  ;   :quantity (USD)
  ;   :side (string)
  ;    "Buy", "Sell"
  ;   :symbol (string)
  ;   :trigger-by (string)(opt)
  ;    "Market price", "Mark price"
  ;   :trigger-price (?)(opt)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; TODO There are more parameters for this API function!
  ;
  ; @example
  ;  (request-order-create! {:api-key "..." :api-secret "..." :category "linear" :quantity 100 :symbol "ETHUSDT"})
  ;  =>
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [{:keys [api-key api-secret quantity symbol use-mainnet?] :as request-props}]
  (let [uri         (order.create.uri/order-create-uri {:use-mainnet?  use-mainnet?})
        form-params (core.request.sign/POST-form-params {:api-key       api-key
                                                         :api-secret    api-secret
                                                         :qty           quantity
                                                         :symbol        symbol
                                                         :order-type    "Limit"
                                                         :price         "3792"
                                                         :side          "Buy"
                                                         :time-in-force "GoodTillCancel"})
        response      (clj-http.client/post uri {:form-params form-params :as :x-www-form-urlencoded})
        response-body (core.response.helpers/POST-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (println "Hello world!"))))
