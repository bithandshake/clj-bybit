
(ns bybit.core.request.body)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn raw-request-body-item
  ; @ignore
  ;
  ; @param (string) name
  ; @param (*) value
  ;
  ; @example
  ; (raw-request-body-item "basePrice" 42)
  ; =>
  ; "\"basePrice\": \"42\""
  ;
  ; @return (string)
  [name value]
  (if value (str "\""name"\": \""value"\"")))

(defn raw-request-body
  ; @ignore
  ;
  ; @param (numbers or strings in vectors in vector) items
  ; [[(string) name
  ;   (number or string)(opt) value]]
  ;
  ; @example
  ; (raw-request-body [["basePrice" "42"] ["symbol" "ETHUSDT"]])
  ; =>
  ; "{\"basePrice\": \"42\", \"symbol\": \"ETHUSDT\"}"
  ;
  ; @return (string)
  [items]
  (letfn [(f [result [name value]]
             (if value (str result (if result ", ") (raw-request-body-item name value))
                       (->  result)))]
         (let [raw-body (reduce f nil items)]
              (str "{"raw-body"}"))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn POST-body
  ; @ignore
  ;
  ; @param (?) items
  [items]
  ; TODO
  (raw-request-body items))
