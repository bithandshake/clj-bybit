
(ns bybit-com.wallet.balance.uri
    (:require [bybit-com.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wallet-balance-query-string
  ; @ignore
  ;
  ; @param (map) query-props
  ;
  ; @return (string)
  [_])

(defn wallet-balance-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (wallet-balance-uri {})
  ; =>
  ; "https://api-testnet.bybit.com/spot/v3/private/account"
  ;
  ; @usage
  ; (wallet-balance-uri {:use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/spot/v3/private/account"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (wallet-balance-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/spot/v3/private/account")))
