
(ns bybit-com.position.list.sample
    (:require [bybit-com.api :as bybit-com]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-position-list!
  []
  (bybit-com/request-position-list! {:api-key "..." :api-secret "..." :category "linear"}))
