
(ns bybit.kline.list.receive
    (:require [bybit.kline.list.utils :as kline.list.utils]
              [reader.api             :as reader]
              [time.api               :as time]
              [vector.api             :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-kline-item
  ; @ignore
  ;
  ; @param (map) kline-item
  ; {:c (string)
  ;  :h (string)
  ;  :l (string)
  ;  :o (string)
  ;  :s (string)
  ;  :sn (string)
  ;  :t (string)
  ;  :v (string)}
  ;
  ; @usage
  ; (receive-kline-item {...})
  ;
  ; @return (map)
  ; {:close (number)
  ;  :close-time (ms)
  ;  :close-timestamp (string)
  ;  :high (number)
  ;  :low (number)
  ;  :open (number)
  ;  :open-time (ms)
  ;  :open-timestamp (string)
  ;  :volume (number)}
  [{:keys [c h l o t v]}]
  ; WARNING!
  ; Close time of the actual ongoing period points to the future!
  {:open-time      t
   :open-timestamp (time/epoch-ms->timestamp-string t)
   :close          (reader/read-str c)
   :open           (reader/read-str o)
   :high           (reader/read-str h)
   :low            (reader/read-str l)
   :volume         (reader/read-str v)})

(defn receive-kline-list
  ; @ignore
  ;
  ; @param (map) kline-list-data
  ; {:kline-list (maps in vector)}
  ;
  ; @usage
  ; (receive-kline-list {...})
  ;
  ; @return (map)
  ; {:kline-list (maps in vector)
  ;  :total-high (integer)
  ;  :total-low (integer)}
  [{:keys [kline-list] :as kline-list-data}]
  (letfn [(f [{:keys [total-high total-low] :as result}
              {:keys [h l]                  :as kline-item}]
             (let [high (reader/read-str h)
                   low  (reader/read-str l)]
                  (-> result (assoc  :total-high (max (or total-high high) high))
                             (assoc  :total-low  (min (or total-low  low)  low))
                             (update :kline-list vector/conj-item (receive-kline-item kline-item)))))]
         (reduce f (dissoc kline-list-data :kline-list) kline-list)))
