
(ns bybit.api
    (:require [bybit.kline.list.request     :as kline.list.request]
              [bybit.order.create.request   :as order.create.request]
              [bybit.position.list.request  :as position.list.request]
              [bybit.quote.ticker.request   :as quote.ticker.request]
              [bybit.wallet.balance.request :as wallet.balance.request]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; bybit.kline.list.request
(def request-kline-list! kline.list.request/request-kline-list!)

; bybit.order.create.request
(def request-order-create! order.create.request/request-order-create!)

; bybit.position.list.request
(def request-position-list! position.list.request/request-position-list!)

; bybit.quote.ticker.request
(def request-quote-ticker! quote.ticker.request/request-quote-ticker!)

; bybit.wallet.balance.request
(def request-wallet-balance! wallet.balance.request/request-wallet-balance!)
