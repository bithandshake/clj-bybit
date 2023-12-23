
(ns bybit-com.kline.list.sample
    (:require [bybit-com.api :as bybit-com]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-kline-list!
  []
  (bybit-com/request-kline-list! {:interval "1m" :limit 60 :symbol "ETHUSDT"}))
