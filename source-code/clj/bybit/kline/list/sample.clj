
(ns bybit.kline.list.sample
    (:require [bybit.api :as bybit]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-kline-list!
  []
  (bybit/request-kline-list! {:interval "1" :limit 60 :symbol "ETHUSDT"}))
