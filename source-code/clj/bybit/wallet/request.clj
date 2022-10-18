
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.wallet.request
    (:require [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [bybit.wallet.uri       :as wallet.uri]
              [clj-http.client        :as clj-http.client]
              [mid-fruits.map         :as map]
              [time.api               :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-wallet-balance!
  ; @param (map) request-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/request-wallet-balance! {:api-key "..." :api-secret "..."})
  ;  =>
  ;  {:api-key  "..."
  ;   :balance  {:BTC {...} ...}
  ;   :time-now "..."
  ;   :uri      "..."}
  ;
  ; @return (map)
  ;  {:api-key (string)
  ;   :balance (map)
  ;   :time-now (string)
  ;   :uri (string)}
  [{:keys [api-key] :as request-props}]
  (let [uri           (wallet.uri/wallet-balance-uri request-props)
        response-body (-> uri clj-http.client/get response.helpers/GET-response->body)]
       (if-not (response.errors/response-body->invalid-api-details? response-body)
               (-> (map/rekey-item response-body :result :balance)
                   (merge {:api-key api-key :uri uri :time-now (time/epoch-s)})))))
