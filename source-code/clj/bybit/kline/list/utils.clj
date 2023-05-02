
(ns bybit.kline.list.utils)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn interval-duration-ms
  ; @param (string) interval
  ; "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;
  ; @example
  ; (interval-duration-ms "1m")
  ; =>
  ; 60000
  ;
  ; @return (ms)
  [interval]
  ; The "1M" (month) period is not here because IDK whether it means 30x1d or
  ; it means calendar months.
  (case interval "1m" 60000 "3m" 180000 "5m" 300000 "15m" 900000 "30m" 1800000 "1h" 3600000 "2h" 7200000 "4h" 14400000
                 "6h" 21600000 "12h" 43200000 "1d" 86400000 "1w" 6048200000 0)) ; 0 as default

(defn close-time-ms
  ; @ignore
  ;
  ; @param (ms) open-time-ms
  ; @param (string) interval
  ; "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;
  ; @example
  ; (close-time-ms 1580183600000 "1m")
  ; =>
  ; 1580183660000
  ;
  ; @return (ms)
  [open-time-ms interval]
  (+ open-time-ms (interval-duration-ms interval)))

(defn query-duration-ms
  ; @ignore
  ;
  ; @param (string) interval
  ; "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ; @param (integer) limit
  ;
  ; @example
  ; (query-duration-ms "1" 60)
  ; =>
  ; 3600000
  ;
  ; @return (ms)
  [interval limit]
  (* limit (interval-duration-ms interval)))
