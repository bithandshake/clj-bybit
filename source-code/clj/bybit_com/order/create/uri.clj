
(ns bybit-com.order.create.uri
    (:require [bybit-com.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (order-create-uri {})
  ; =>
  ; "https://api-testnet.bybit.com/spot/v3/private/order"
  ;
  ; @usage
  ; (order-create-uri {:use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/spot/v3/private/order"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [address (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/spot/v3/private/order")))
