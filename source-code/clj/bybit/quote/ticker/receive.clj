
(ns bybit.quote.ticker.receive
    (:require [reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-quote-ticker
  ; @ignore
  ;
  ; @param (map) quote-ticker-data
  ; {:result (map)
  ;   {}}
  ;
  ; @usage
  ; (receive-quote-ticker {...})
  ;
  ; @return (map)
  ; {}
  [{:keys [result] :as quote-ticker-data}]
  (-> quote-ticker-data (dissoc :result)
                        (assoc  :ticker {:best-ask-price    (-> result :ap reader/read-str)
                                         :best-bid-price    (-> result :bp reader/read-str)
                                         :high-price        (-> result :h  reader/read-str)
                                         :last-traded-price (-> result :lp reader/read-str)
                                         :low-price         (-> result :l  reader/read-str)
                                         :open-price        (-> result :o  reader/read-str)
                                         :quote-volume      (-> result :qv reader/read-str)
                                         :volume            (-> result :v  reader/read-str)
                                         :current-time      (:t result)
                                         :symbol            (:s result)})))
