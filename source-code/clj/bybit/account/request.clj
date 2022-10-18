
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.account.request
    (:require [bybit.account.uri      :as account.uri]
              [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [clj-http.client        :as clj-http.client]
              [mid-fruits.map         :as map]
              [time.api               :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-account-api-key!
  ; @param (map) request-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/request-account-api-key! {:api-key "..." :api-secret "..."})
  ;  =>
  ;  {}
  ;
  ; @return (map)
  ;  {:api-key (string)
  ;   :api-key-info (maps in vector)
  ;   :time-now (string)
  ;   :uri (string)}
  [{:keys [api-key] :as request-props}]
  (let [uri           (account.uri/account-api-key-uri request-props)
        response-body (-> uri clj-http.client/get response.helpers/GET-response->body)]
       (if-not (response.errors/response-body->invalid-api-details? response-body)
               (-> (map/rekey-item response-body :result :api-key-info)
                   (merge {:api-key api-key :uri uri :time-now (time/epoch-s)})))))
