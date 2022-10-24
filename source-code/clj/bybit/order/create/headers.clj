
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.order.create.headers
    (:require [bybit.order.create.uri :as order.create.uri]
              [server-fruits.hash     :as hash]
              [time.api               :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn order-create-param-string
  ; @param (map) param-props
  ;  {}
  ;
  ; @return (string)
  [{:keys [] :as param-props}])

(defn order-create-headers
  ; @param (map) uri-props
  ;  {}
  ;
  ; @usage
  ;  (order-create-headers {})
  ;
  ; @usage
  ;  (order-create-headers {:use-mainnet? true})
  ;
  ; @return (map)
  ;  {}
  [{:keys [] :as headers-props}])
