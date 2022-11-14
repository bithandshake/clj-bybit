
(ns bybit.api
    (:require [bybit.core.response.errors   :as core.response.errors]
              [bybit.core.response.helpers  :as core.response.helpers]
              [bybit.core.uri.config        :as core.uri.config]
              [bybit.kline.list.request     :as kline.list.request]
              [bybit.kline.list.uri         :as kline.list.uri]
              [bybit.order.create.headers   :as order.create.headers]
              [bybit.order.create.request   :as order.create.request]
              [bybit.order.create.uri       :as order.create.uri]
              [bybit.position.list.headers  :as position.list.headers]
              [bybit.position.list.request  :as position.list.request]
              [bybit.position.list.uri      :as position.list.uri]
              [bybit.wallet.balance.headers :as wallet.balance.headers]
              [bybit.wallet.balance.request :as wallet.balance.request]
              [bybit.wallet.balance.uri     :as wallet.balance.uri]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; bybit.core.uri.config
(def API-ADDRESS      core.uri.config/API-ADDRESS)
(def TEST-API-ADDRESS core.uri.config/TEST-API-ADDRESS)

; bybit.core.response.errors
(def response-body->error?               core.response.errors/response-body->error?)
(def response-body->invalid-api-details? core.response.errors/response-body->invalid-api-details?)

; bybit.core.response.helpers
(def time-now->epoch-ms     core.response.helpers/time-now->epoch-ms)
(def GET-response->body     core.response.helpers/GET-response->body)
(def POST-response->headers core.response.helpers/POST-response->headers)
(def POST-response->body    core.response.helpers/POST-response->body)

; bybit.kline.list.request
(def request-kline-list! kline.list.request/request-kline-list!)

; bybit.kline.list.uri
(def kline-list-uri      kline.list.uri/kline-list-uri)
(def kline-list-uri-list kline.list.uri/kline-list-uri-list)

; bybit.order.create.headers
(def position-list-headers order.create.headers/order-create-headers)

; bybit.order.create.request
(def request-order-create! order.create.request/request-order-create!)

; bybit.order.create.uri
(def order-create-uri order.create.uri/order-create-uri)

; bybit.position.list.headers
(def position-list-headers position.list.headers/position-list-headers)

; bybit.position.list.request
(def request-position-list! position.list.request/request-position-list!)

; bybit.position.list.uri
(def position-list-uri position.list.uri/position-list-uri)

; bybit.wallet.balance.headers
(def wallet-balance-headers wallet.balance.headers/wallet-balance-headers)

; bybit.wallet.balance.request
(def request-wallet-balance! wallet.balance.request/request-wallet-balance!)

; bybit.wallet.balance.uri
(def wallet-balance-uri wallet.balance.uri/wallet-balance-uri)
