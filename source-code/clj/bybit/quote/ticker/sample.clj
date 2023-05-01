
(ns bybit.quote.ticker.sample
    (:require [bybit.api :as bybit]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-my-quote-ticker!
  []
  (bybit/request-quote-ticker! {:symbol "ETHUSDT"}))
