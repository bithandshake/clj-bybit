
(ns bybit.core.response.errors)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn response-body->error?
  ; @ignore
  ;
  ; @param (map) response-body
  ; {:ret-code (integer)}
  ;
  ; @usage
  ; (response-body->error? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code]}]
  (-> ret-code zero? not))

(defn response-body->invalid-api-details?
  ; @ignore
  ;
  ; @param (map) response-body
  ; {:ret-code (integer)}
  ;
  ; @usage
  ; (response-body->invalid-api-details? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code]}]
  (or (= ret-code 10003)
      (= ret-code 10004)))
