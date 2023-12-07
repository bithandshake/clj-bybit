
(ns bybit.quote.ticker.receive
    (:require [fruits.reader.api :as reader]))

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
                        (assoc  :ticker {:best-ask-price    (-> result :ap reader/read-edn)
                                         :best-bid-price    (-> result :bp reader/read-edn)
                                         :high-price        (-> result :h  reader/read-edn)
                                         :last-traded-price (-> result :lp reader/read-edn)
                                         :low-price         (-> result :l  reader/read-edn)
                                         :open-price        (-> result :o  reader/read-edn)
                                         :quote-volume      (-> result :qv reader/read-edn)
                                         :volume            (-> result :v  reader/read-edn)
                                         :current-time      (:t result)
                                         :symbol            (:s result)})))
