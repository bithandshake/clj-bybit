
(ns bybit-com.api
    (:require [bybit-com.kline.list.request     :as kline.list.request]
              [bybit-com.kline.list.utils       :as kline.list.utils]
              [bybit-com.order.create.request   :as order.create.request]
              [bybit-com.position.list.request  :as position.list.request]
              [bybit-com.quote.ticker.request   :as quote.ticker.request]
              [bybit-com.wallet.balance.request :as wallet.balance.request]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @tutorial Testnet / Mainnet
;
; The requesting functions takes the '{:use-mainnet? ...}' parameter, which provides
; you the choice of whether the 'api-testnet.bybit.com' or the 'api.bybit.com' server should be targeted.

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (bybit-com.kline.list.request/*)
(def request-kline-list! kline.list.request/request-kline-list!)

; @redirect (bybit-com.kline.list.utils/*)
(def interval-duration-ms kline.list.utils/interval-duration-ms)

; @redirect (bybit-com.order.create.request/*)
(def request-order-create! order.create.request/request-order-create!)

; @redirect (bybit-com.position.list.request/*)
(def request-position-list! position.list.request/request-position-list!)

; @redirect (bybit-com.quote.ticker.request/*)
(def request-quote-ticker! quote.ticker.request/request-quote-ticker!)

; @redirect (bybit-com.wallet.balance.request/*)
(def request-wallet-balance! wallet.balance.request/request-wallet-balance!)
