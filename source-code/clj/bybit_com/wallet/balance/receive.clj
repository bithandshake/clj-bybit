
(ns bybit-com.wallet.balance.receive
    (:require [fruits.json.api :as json]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-wallet-balance
  ; @ignore
  ;
  ; @param (map) wallet-balance-data
  ; {:wallet-balance (maps in vector)}
  ;
  ; @usage
  ; (receive-wallet-balance {...})
  ;
  ; @return (map)
  [wallet-balance-data]
  (json/parse-number-values wallet-balance-data))
