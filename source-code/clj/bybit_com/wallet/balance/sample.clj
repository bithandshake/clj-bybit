
(ns bybit-com.wallet.balance.sample
    (:require [bybit-com.api :as bybit-com]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-wallet-balance!
  []
  (bybit-com/request-wallet-balance! {:api-key "..." :api-secret "..."}))
