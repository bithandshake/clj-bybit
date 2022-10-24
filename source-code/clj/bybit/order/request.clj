
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.order.request
    (:require [bybit.order.uri        :as order.uri]
              [bybit.request.sign     :as request.sign]
              [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [clj-http.client        :as clj-http.client]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-order-create!
  ; @param (map) request-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :quantity (USD)
  ;   :symbol (string)
  ;    "BTCUSD", "ETHUSD"
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/request-order-create! {:api-key "..." :api-secret "..." :quantity 100 :symbol "ETHUSD"})
  ;  =>
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [{:keys [api-key api-secret quantity symbol use-mainnet?] :as request-props}]
  (let [uri         (order.uri/order-create-uri    {:use-mainnet?  use-mainnet?})
        form-params (request.sign/POST-form-params {:api-key       api-key
                                                    :api-secret    api-secret
                                                    :qty           quantity
                                                    :symbol        symbol
                                                    :order-type    "Limit"
                                                    :price         "3792"
                                                    :side          "Buy"
                                                    :time-in-force "GoodTillCancel"})
        response      (clj-http.client/post uri {:form-params form-params :as :x-www-form-urlencoded})
        response-body (response.helpers/POST-response->body response)]
       (if-not (response.errors/response-body->error? response-body)
               (println "Hello world!"))))
