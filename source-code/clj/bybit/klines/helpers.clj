
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.klines.helpers
    (:require [mid-fruits.candy  :refer [param]]
              [mid-fruits.reader :as reader]
              [mid-fruits.vector :as vector]
              [time.api          :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn interval-duration
  ; @param (string) interval
  ;  "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;
  ; @example
  ;  (bybit/interval-duration "1")
  ;  =>
  ;  60
  ;
  ; @return (s)
  [interval]
  (case interval "1" 60 "3" 180 "5" 300 "15" 900 "30" 1800 "60" 3600 "120" 7200 "240" 14400
                 "360" 21600 "720" 43200 "D" 86400 "M" 1152000 "W" 6048200 0)) ; 0 as default

(defn close-time
  ; @param (s) open-time
  ; @param (string) interval
  ;  "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;
  ; @example
  ;  (bybit/close-time 1580183600 "1")
  ;  =>
  ;  1580183660
  ;
  ; @return (s)
  [open-time interval]
  (+ open-time (interval-duration interval)))

(defn query-duration
  ; @param (string) interval
  ;  "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ; @param (integer) limit
  ;
  ; @example
  ;  (bybit/query-duration "1" 60)
  ;  =>
  ;  3600
  ;
  ; @return (s)
  [interval limit]
  (* limit (interval-duration interval)))

(defn query-from
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (string) interval
  ;  "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ; @param (integer) limit
  ; @param (s)(opt) epoch-s
  ;
  ; @example
  ;  (bybit/query-from "1" 60 1580183600)
  ;  =>
  ;  1580180000
  ;
  ; @return (s)
  ([interval limit]
   (- (time/epoch-s)
      (query-duration interval limit)))

  ([interval limit epoch-s]
   (- epoch-s (query-duration interval limit))))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn receive-kline-item
  ; @param (map) kline-item
  ;  {:close (string)
  ;   :high (string)
  ;   :low (string)
  ;   :open (string)
  ;   :open-time (string)
  ;   :volume (string)}
  ;
  ; @usage
  ;  (bybit/receive-kline-item {...})
  ;
  ; @return (map)
  ;  {:close (number)
  ;   :close-time (integer)
  ;   :close-timestamp (string)
  ;   :high (number)
  ;   :low (number)
  ;   :open (number)
  ;   :open-time (integer)
  ;   :open-timestamp (string)
  ;   :volume (number)}
  [{:keys [close high interval low open open-time volume] :as kline-item}]
  (let [; WARNING! Az aktuális (éppen történő) periódus close-time értéke egy jövőbeni időpontra mutat!
        close-time (close-time open-time interval)]
       (-> kline-item (dissoc :open-time :symbol)
                      (assoc  :close-time      (param close-time))
                      (assoc  :open-time       (param  open-time))
                      (assoc  :open-timestamp  (time/epoch-s->timestamp-string  open-time))
                      (assoc  :close-timestamp (time/epoch-s->timestamp-string close-time))
                      (assoc  :close           (reader/read-str close))
                      (assoc  :open            (reader/read-str open))
                      (assoc  :high            (reader/read-str high))
                      (assoc  :low             (reader/read-str low))
                      (assoc  :volume          (reader/read-str volume)))))

(defn receive-kline-list-data
  ; @param (map) kline-list-data
  ;  {:kline-list (maps in vector)}
  ;
  ; @usage
  ;  (bybit/receive-kline-list-data {...})
  ;
  ; @return (map)
  ;  {:kline-list (maps in vector)
  ;   :total-high (integer)
  ;   :total-low (integer)}
  [{:keys [kline-list] :as kline-list-data}]
  (letfn [(f [{:keys [total-high total-low] :as o} {:keys [close open] :as x}]
             (let [close (reader/read-str close)
                   open  (reader/read-str open)]
                  ; - A total-high és total-low értékek beállításakor az árfolyam nyitóértékét
                  ;   is figyelembe kell venni!
                  ; - A kliens-oldali árfolyam-diagram sávjai az egyes periódusok nyitó-
                  ;   és záró-pontja közötti értékeket rajzolják ki.
                  (cond-> o ; Set initial total-high ...
                            (nil? total-high) (assoc :total-high (max open close))
                            ; Set initial total-low ...
                            (nil? total-low)  (assoc :total-low  (min open close))
                            ; If close is higher than total-high ...
                            (and (some? total-high) (< total-high close))
                            (assoc :total-high close)
                            ; If total-low is higher than close ...
                            (and (some? total-low)  (> total-low  close))
                            (assoc :total-low close)
                            ; Update kline-item ...
                            :update-kline-item (update :kline-list vector/conj-item (receive-kline-item x)))))]
         (reduce f (dissoc kline-list-data :kline-list) kline-list)))
