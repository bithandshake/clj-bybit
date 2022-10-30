
(ns bybit.core.response.errors)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn response-body->error?
  ; @param (map) response-body
  ;  {:ret-code (integer)}
  ;
  ; @usage
  ;  (response-body->error? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code]}]
  (not= ret-code 0))

(defn response-body->invalid-api-details?
  ; @param (map) response-body
  ;  {:ret-code (integer)}
  ;
  ; @usage
  ;  (response-body->invalid-api-details? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code]}]
  (or (= ret-code 10003)
      (= ret-code 10004)))
