
(ns bybit.order.create.uri
    (:require [bybit.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-uri
  ; @param (map) uri-props
  ; {:use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (order-create-uri {})
  ; =>
  ; "https://api-testnet.bybit.com/unified/v3/private/order/create"
  ;
  ; @example
  ; (order-create-uri {:use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/unified/v3/private/order/create"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [address (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/order/create")))
