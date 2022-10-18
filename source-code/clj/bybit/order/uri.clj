
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.order.uri
    (:require [bybit.uri.config :as uri.config]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-uri
  ; @param (map) uri-props
  ;  {:use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/order-create-uri {})
  ;  =>
  ;  https://api-testnet.bybit.com/v2/private/order/create
  ;
  ; @example
  ;  (bybit/order-create-uri {:use-mainnet? true})
  ;  =>
  ;  https://api.bybit.com/v2/private/order/create
  ;
  ; @return (string)
  [{:keys [use-mainnet?]}]
  (let [address (if use-mainnet? uri.config/PRIVATE-API-ADDRESS uri.config/PRIVATE-TEST-API-ADDRESS)]
       (str address "/order/create")))
