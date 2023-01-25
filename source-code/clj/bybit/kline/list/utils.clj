
(ns bybit.kline.list.utils
    (:require [time.api :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn interval-duration
  ; @ignore
  ;
  ; @param (string) interval
  ; "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;
  ; @example
  ; (interval-duration "1")
  ; =>
  ; 60000
  ;
  ; @return (ms)
  [interval]
  (case interval "1" 60000 "3" 180000 "5" 300000 "15" 900000 "30" 1800000 "60" 3600000 "120" 7200000 "240" 14400000
                 "360" 21600000 "720" 43200000 "D" 86400000 "M" 1152000000 "W" 6048200000 0)) ; 0 as default

(defn close-time
  ; @ignore
  ;
  ; @param (ms) open-time
  ; @param (string) interval
  ; "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;
  ; @example
  ; (close-time 1580183600000 "1")
  ; =>
  ; 1580183660000
  ;
  ; @return (ms)
  [open-time interval]
  (+ open-time (interval-duration interval)))

(defn query-duration
  ; @ignore
  ;
  ; @param (string) interval
  ; "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ; @param (integer) limit
  ;
  ; @example
  ; (query-duration "1" 60)
  ; =>
  ; 3600000
  ;
  ; @return (ms)
  [interval limit]
  (* limit (interval-duration interval)))

(defn query-start
  ; @ignore
  ;
  ; @param (string) interval
  ; "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ; @param (integer) limit
  ; @param (ms)(opt) epoch-ms
  ;
  ; @example
  ; (query-start "1" 60 1580183600000)
  ; =>
  ; 1580180000000
  ;
  ; @return (ms)
  ([interval limit]
   (- (time/epoch-ms)
      (query-duration interval limit)))

  ([interval limit epoch-ms]
   (- epoch-ms (query-duration interval limit))))
