
(ns bybit.order.create.receive
    (:require [mid-fruits.json :as json]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-order-created
  ; @param (map) order-created-data
  ;  {:order-created (maps in vector)}
  ;
  ; @usage
  ;  (receive-order-created {...})
  ;
  ; @return (map)
  [order-created-data]
  ;(json/parse-number-values order-created-data))
  order-created-data)
