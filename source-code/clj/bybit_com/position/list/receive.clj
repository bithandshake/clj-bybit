
(ns bybit-com.position.list.receive
    (:require [fruits.json.api :as json]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-position-list
  ; @ignore
  ;
  ; @param (map) position-list-data
  ; {:position-list (maps in vector)}
  ;
  ; @usage
  ; (receive-position-list {...})
  ;
  ; @return (map)
  [position-list-data]
  (json/parse-number-values position-list-data))
