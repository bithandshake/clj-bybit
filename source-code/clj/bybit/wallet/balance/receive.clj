
(ns bybit.wallet.balance.receive
    (:require [mid-fruits.json :as json]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-wallet-balance
  ; @param (map) wallet-balance-data
  ;  {:wallet-balance (maps in vector)}
  ;
  ; @usage
  ;  (receive-wallet-balance {...})
  ;
  ; @return (map)
  [wallet-balance-data]
  (json/parse-number-values wallet-balance-data))
