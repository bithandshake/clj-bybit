
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.api
    (:require [bybit.account.request  :as account.request]
              [bybit.account.uri      :as account.uri]
              [bybit.klines.errors    :as klines.errors]
              [bybit.klines.helpers   :as klines.helpers]
              [bybit.klines.request   :as klines.request]
              [bybit.klines.uri       :as klines.uri]
              [bybit.order.request    :as order.request]
              [bybit.order.uri        :as order.uri]
              [bybit.request.sign     :as request.sign]
              [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [bybit.uri.config       :as uri.config]
              [bybit.wallet.request   :as wallet.request]
              [bybit.wallet.uri       :as wallet.uri]))



;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; bybit.account.request
(def request-account-api-key! account.request/request-account-api-key!)

; bybit.account.uri
(def account-api-key-uri account.uri/account-api-key-uri)

; bybit.klines.errors
(def kline-list-data->time-error  klines.errors/kline-list-data->time-error)
(def kline-list-data->limit-error klines.errors/kline-list-data->limit-error)
(def kline-list-data->error       klines.errors/kline-list-data->error)
(def kline-list-data<-error       klines.errors/kline-list-data<-error)

; bybit.klines.helpers
(def interval-duration       klines.helpers/interval-duration)
(def close-time              klines.helpers/close-time)
(def query-duration          klines.helpers/query-duration)
(def query-from              klines.helpers/query-from)
(def receive-kline-item      klines.helpers/receive-kline-item)
(def receive-kline-list-data klines.helpers/receive-kline-list-data)

; bybit.klines.request
(def request-kline-list! klines.request/request-kline-list!)

; bybit.klines.uri
(def kline-list-uri      klines.uri/kline-list-uri)
(def kline-list-uri-list klines.uri/kline-list-uri-list)

; bybit.order.request
(def request-order-create! order.request/request-order-create!)

; bybit.order.uri
(def order-create-uri order.uri/order-create-uri)

; bybit.request.sign
(def signed-query-string bybit.request.sign/signed-query-string)
(def signed-form-params  bybit.request.sign/signed-form-params)
(def POST-form-params    bybit.request.sign/POST-form-params)

; bybit.response.helpers
(def time-now->epoch-ms     response.helpers/time-now->epoch-ms)
(def GET-response->body     response.helpers/GET-response->body)
(def POST-response->headers response.helpers/POST-response->headers)
(def POST-response->body    response.helpers/POST-response->body)

; bybit.response.errors
(def response-body->error?               bybit.response.errors/response-body->error?)
(def response-body->invalid-api-details? bybit.response.errors/response-body->invalid-api-details?)

; bybit.uri.config
(def PUBLIC-API-ADDRESS       uri.config/PUBLIC-API-ADDRESS)
(def PUBLIC-TEST-API-ADDRESS  uri.config/PUBLIC-TEST-API-ADDRESS)
(def PRIVATE-API-ADDRESS      uri.config/PRIVATE-API-ADDRESS)
(def PRIVATE-TEST-API-ADDRESS uri.config/PRIVATE-TEST-API-ADDRESS)

; bybit.wallet.request
(def request-wallet-balance! wallet.request/request-wallet-balance!)

; bybit.wallet.uri
(def wallet-balance-uri wallet.uri/wallet-balance-uri)
