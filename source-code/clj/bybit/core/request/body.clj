
(ns bybit.core.request.body
    (:require [server-fruits.hash :as hash]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn raw-request-body-item
  ; @param (string) name
  ; @param (*) value
  ;
  ; @example
  ;  (raw-request-body-item "basePrice" 42)
  ;  =>
  ;  "\"basePrice\": \"42\""
  ;
  ; @return (string)
  [name value]
  (if value (str "\""name"\": \""value"\"")))

(defn raw-request-body
  ; @param (numbers or strings in vectors in vector) items
  ;  [[(string) name
  ;    (number or string) value]]
  ;
  ; @example
  ;  (raw-request-body [["basePrice" "42"] ["symbol" "ETHUSDT"]])
  ;  =>
  ;  "{\"basePrice\": \"42\", \"symbol\": \"ETHUSDT\"}"
  ;
  ; @return (string)
  [items]
  (letfn [(f [result dex [name value]] (str (if-not (= dex 0) ", ") (raw-request-body-item name value)))]
         (str "{"(reduce-kv f items))"}"))
