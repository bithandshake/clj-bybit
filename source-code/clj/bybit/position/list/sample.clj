
(ns bybit.position.list.sample
    (:require [bybit.api :as bybit]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-position-list!
  []
  (bybit/request-position-list! {:api-key "..." :api-secret "..." :category "linear"}))
