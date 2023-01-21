
(ns bybit.kline.list.receive
    (:require [bybit.kline.list.helpers :as kline.list.helpers]
              [reader.api               :as reader]
              [time.api                 :as time]
              [vector.api               :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-kline-item
  ; @ignore
  ;
  ; @param (vector) kline-item
  ; [(ms) start
  ;  (string) open
  ;  (string) high
  ;  (string) low
  ;  (string) close
  ;  (string) volume
  ;  (string) turnover]
  ;
  ; @usage
  ; (receive-kline-item [...])
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
  [[start open high low close volume _]]
  (let [open-time (reader/read-str start)]
        ; WARNING! Az aktuális (éppen történő) periódus close-time értéke egy jövőbeni időpontra mutat!
        ; close-time (kline.list.helpers/close-time start interval)]
       {:open-time       open-time
       ;:close-item      close-time
        :open-timestamp  (time/epoch-ms->timestamp-string open-time)
       ;:close-timestamp (time/epoch-ms->timestamp-string close-time)
        :close           (reader/read-str close)
        :open            (reader/read-str open)
        :high            (reader/read-str high)
        :low             (reader/read-str low)
        :volume          (reader/read-str volume)}))

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
  (letfn [(f [{:keys [total-high total-low] :as result} [_ _ high low _ _ _ :as kline-item]]
             (let [high (reader/read-str high)
                   low  (reader/read-str low)]
                  (-> result (assoc  :total-high (max (or total-high high) high))
                             (assoc  :total-low  (min (or total-low  low)  low))
                             (update :kline-list vector/cons-item (receive-kline-item kline-item)))))]
         (reduce f (dissoc kline-list-data :kline-list) kline-list)))
