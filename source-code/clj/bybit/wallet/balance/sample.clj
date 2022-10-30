
(ns bybit.wallet.balance.sample
    (:require [bybit.api :as bybit]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-wallet-balance!
  []
  (bybit/request-wallet-balance! {:api-key "..." :api-secret "..."}))
