
(ns bybit.order.create.uri
    (:require [bybit.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (order-create-uri {})
  ; =>
  ; "https://api-testnet.bybit.com/spot/v3/private/order"
  ;
  ; @example
  ; (order-create-uri {:use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/spot/v3/private/order"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [address (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/spot/v3/private/order")))
