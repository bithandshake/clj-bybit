
(ns bybit-com.quote.ticker.sample
    (:require [bybit-com.api :as bybit-com]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-quote-ticker!
  []
  (bybit-com/request-quote-ticker! {:symbol "ETHUSDT"}))
