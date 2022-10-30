
(ns bybit.core.request.sign
    (:require [mid-fruits.json    :as json]
              [mid-fruits.loop    :refer [reduce-indexed]]
              [mid-fruits.map     :as map]
              [mid-fruits.vector  :as vector]
              [server-fruits.hash :as hash]
              [time.api           :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn signed-query-string
  ; @param (string) query-string
  ; @param (string) api-secret
  ;
  ; @example
  ;  (signed-query-string "..." "...")
  ; =>
  ; "..."
  ;
  ; @return (string)
  [query-string api-secret]
  (let [sign (hash/hmac-sha256 query-string api-secret)]
       (str query-string "&sign=" sign)))

(defn signed-form-params
  ; @param (map) form-params
  ; @param (string) api-secret
  ;
  ; @example
  ;  (signed-form-params {...} "...")
  ;  =>
  ;  {...}
  ;
  ; @return (map)
  ;  {:sign (string)}
  [form-params api-secret]
  (let [ordered-keys (-> form-params map/get-keys vector/abc-items)]
       (letfn [(f [o dex x]
                  (if (vector/dex-first? dex)
                      (str o     (name x) "=" (get form-params x))
                      (str o "&" (name x) "=" (get form-params x))))]
              (let [query-string (reduce-indexed   f "" ordered-keys)
                    sign         (hash/hmac-sha256 query-string api-secret)]
                   (assoc form-params :sign sign)))))

(defn POST-form-params
  ; @param (map) form-params
  ;  {:api-secret (string)}
  ;
  ; @example
  ;  (signed-form-params {...} "...")
  ;  =>
  ;  {...}
  ;
  ; @return (map)
  ;  {:sign (string)
  ;   :timestamp (string)}
  [{:keys [api-secret] :as form-params}]
  (-> form-params (dissoc :api-secret)
                  (assoc  :timestamp (time/epoch-ms))
                  (json/underscore-keys)
                  (signed-form-params api-secret)))
