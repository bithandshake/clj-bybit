
(ns bybit-com.quote.ticker.receive
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
                        (assoc  :ticker {:best-ask-price    (-> result :ap reader/parse-edn)
                                         :best-bid-price    (-> result :bp reader/parse-edn)
                                         :high-price        (-> result :h  reader/parse-edn)
                                         :last-traded-price (-> result :lp reader/parse-edn)
                                         :low-price         (-> result :l  reader/parse-edn)
                                         :open-price        (-> result :o  reader/parse-edn)
                                         :quote-volume      (-> result :qv reader/parse-edn)
                                         :volume            (-> result :v  reader/parse-edn)
                                         :current-time      (:t result)
                                         :symbol            (:s result)})))
