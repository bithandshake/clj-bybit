
(ns bybit.order.create.request
    (:require [bybit.core.request.sign     :as core.request.sign]
              [bybit.core.response.errors  :as core.response.errors]
              [bybit.core.response.helpers :as core.response.helpers]
              [bybit.order.create.receive  :as order.create.receive]
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
  ;   :close-on-trigger? (boolean)(opt)
  ;    Default: false
  ;   :implied-volatility (string)(opt)
  ;   :order-link-id (string)(opt)
  ;   :order-type (string)
  ;    "Limit", "Market"
  ;   :position-dex (string)(opt)
  ;   :price (?)(opt)
  ;   :protect-market-maker? (boolean)(opt)
  ;    Default: false
  ;   :quantity (USD)
  ;   :reduce-only? (boolean)(opt)
  ;    Default: false
  ;   :side (string)
  ;    "Buy", "Sell"
  ;   :sl-trigger-by (string)(opt)
  ;   :stop-loss (?)(opt)
  ;   :symbol (string)
  ;   :take-profit (?)(opt)
  ;   :time-in-force (string)
  ;    "FillOrKill", "GoodTillCancel", "ImmediateOrCancel", "PostOnly"
  ;   :tp-trigger-by (string)(opt)
  ;   :trigger-by (string)(opt)
  ;    "Market price", "Mark price"     <---- ez
  ;    "IndexPrice", "LastPrice", "MarkPrice"    <---- vagy ez
  ;   :trigger-price (?)(opt)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (request-order-create! {:api-key "..." :api-secret "..." :category "linear" :quantity 100 :symbol "ETHUSDT"})
  ;  =>
  ;  {}
  ;
  ; @return (map)
  ;  {}
  [{:keys [use-mainnet?] :as request-props}]
  (let [uri         (order.create.uri/order-create-uri {:use-mainnet? use-mainnet?})]
       uri))

        ;response      (clj-http.client/post uri {:form-params form-params :as :x-www-form-urlencoded})
        ;response-body (core.response.helpers/POST-response->body response)]
       ;(if (core.response.errors/response-body->error? response-body)
        ;   (return response-body)
        ;   response-body]))





;        curl --location --request POST 'https://api-testnet.bybit.com/unified/v3/private/order/create' \
;        --header 'X-BAPI-SIGN-TYPE: 2' \
;        --header 'X-BAPI-SIGN: 27ecc5cca2aec6a779c9f5ef828275a3e9ccaf392a030820911c52d6085c34db' \
;        --header 'X-BAPI-API-KEY: {api key}' \
;        --header 'X-BAPI-TIMESTAMP: 1657871228347' \
;        --header 'X-BAPI-RECV-WINDOW: 5000' \
;        --header 'Content-Type: application/json' \
;        --data-raw '{}
;            "symbol": "BTCUSDT",
;            "orderType": "Limit",
;            "side": "Buy",
;            "qty": "0.01",
;            "price": "20002",
;            "timeInForce": "GoodTillCancel",
;            "category": "linear"
;        '))
