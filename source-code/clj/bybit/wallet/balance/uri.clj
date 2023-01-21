
(ns bybit.wallet.balance.uri
    (:require [bybit.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wallet-balance-query-string
  ; @ignore
  ;
  ; @param (map) query-props
  ; {:coin (string)(opt)}
  ;
  ; @return (string)
  [{:keys [coin]}]
  (str "coin=" coin))

(defn wallet-balance-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:coin (string)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (wallet-balance-uri {})
  ; =>
  ; "https://api-testnet.bybit.com/unified/v3/private/account/wallet/balance"
  ;
  ; @example
  ; (wallet-balance-uri {:use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/unified/v3/private/account/wallet/balance"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (wallet-balance-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/account/wallet/balance?" query-string)))
