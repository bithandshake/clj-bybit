
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.response.errors)



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn response-body->error?
  ; @param (map) response-body
  ;  {:ret-code (integer)
  ;   :ret-msg (string)}
  ;
  ; @usage
  ;  (bybit/response-body->error? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code ret-msg]}]
  (or (not= ret-code 0)
      (not= ret-msg "OK")))

(defn response-body->invalid-api-details?
  ; @param (map) response-body
  ;  {:ret-code (integer)}
  ;
  ; @usage
  ;  (bybit/response-body->invalid-api-details? {...})
  ;
  ; @return (boolean)
  [{:keys [ret-code]}]
  (or (= ret-code 10003)
      (= ret-code 10004)))
