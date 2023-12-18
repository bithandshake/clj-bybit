
(ns bybit.api
    (:require [bybit.kline.list.request     :as kline.list.request]
              [bybit.kline.list.utils       :as kline.list.utils]
              [bybit.order.create.request   :as order.create.request]
              [bybit.position.list.request  :as position.list.request]
              [bybit.quote.ticker.request   :as quote.ticker.request]
              [bybit.wallet.balance.request :as wallet.balance.request]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (bybit.kline.list.request/*)
(def request-kline-list! kline.list.request/request-kline-list!)

; @redirect (bybit.kline.list.utils/*)
(def interval-duration-ms kline.list.utils/interval-duration-ms)

; @redirect (bybit.order.create.request/*)
(def request-order-create! order.create.request/request-order-create!)

; @redirect (bybit.position.list.request/*)
(def request-position-list! position.list.request/request-position-list!)

; @redirect (bybit.quote.ticker.request/*)
(def request-quote-ticker! quote.ticker.request/request-quote-ticker!)

; @redirect (bybit.wallet.balance.request/*)
(def request-wallet-balance! wallet.balance.request/request-wallet-balance!)
