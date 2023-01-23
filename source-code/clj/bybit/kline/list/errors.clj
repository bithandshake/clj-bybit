
(ns bybit.kline.list.errors
    (:require [bybit.kline.list.helpers :as kline.list.helpers]
              [loop.api                 :refer [some-indexed]]
              [noop.api                 :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn kline-list-data->time-error
  ; @ignore
  ;
  ; @param (map) kline-list-data
  ; {:kline-list (maps in vector)}
  ; @param (map) options
  ;
  ; @return (namespaced keyword)
  ; :time-error/different-intervals, :time-error/time-slippage
  [{:keys [kline-list]} _]
  (letfn [(f [dex x]
             (cond ; Az első elemet nincs mivel összehasonlítani ...
                   (= dex 0) (return nil)
                   ; Ha az interval értéke nem egyezik meg az előző elem interval értékével ...
                   (not= (get-in kline-list [(dec dex) :interval])
                         (:interval x))
                   (return :time-error/different-intervals)
                   ; Ha az open-time értéke nem egyenlő az előző elem open-time értékének
                   ; és a periódus hosszának összegével ...
                   (not= (+ (get-in kline-list [(dec dex) :open-time])
                            (kline.list.helpers/interval-duration (:interval x)))
                         (:open-time x))
                   (return :time-error/time-slippage)))]
         (some-indexed f kline-list)))

(defn kline-list-data->limit-error
  ; @ignore
  ;
  ; @param (map) kline-list-data
  ; {:kline-list (maps in vector)}
  ; @param (map) options
  ; {:limit (integer)}
  ;
  ; @return (namespaced keyword)
  ; :limit-error/too-few-kline, :limit-error/too-many-kline
  [{:keys [kline-list]} {:keys [limit]}]
  (cond (> limit (count kline-list)) (return :limit-error/too-few-kline)
        (< limit (count kline-list)) (return :limit-error/too-many-kline)))

(defn kline-list-data->error
  ; @ignore
  ;
  ; @param (map) kline-list-data
  ; {:kline-list (maps in vector)}
  ; @param (map) options
  ; {:limit (integer)}
  ;
  ; @return (namespaced keyword)
  [kline-list-data options]
  (or (kline-list-data->time-error  kline-list-data options)
      (kline-list-data->limit-error kline-list-data options)))

(defn kline-list-data<-error
  ; @ignore
  ;
  ; @param (map) kline-list-data
  ; {:kline-list (maps in vector)}
  ; @param (map) options
  ; {:limit (integer)}
  ;
  ; @return (map)
  ; {:error (namespaced keyword)}
  [kline-list-data options]
  (if-let [error (kline-list-data->error kline-list-data options)]
          (assoc  kline-list-data :error error)
          (return kline-list-data)))
