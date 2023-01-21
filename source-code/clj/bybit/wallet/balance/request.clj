
(ns bybit.wallet.balance.request
    (:require [bybit.core.response.errors   :as core.response.errors]
              [bybit.core.response.helpers  :as core.response.helpers]
              [bybit.wallet.balance.headers :as wallet.balance.headers]
              [bybit.wallet.balance.receive :as wallet.balance.receive]
              [bybit.wallet.balance.uri     :as wallet.balance.uri]
              [candy.api                    :refer [return]]
              [clj-http.client              :as clj-http.client]
              [time.api                     :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-wallet-balance!
  ; @ignore
  ;
  ; @param (map) request-props
  ; {:api-key (string)
  ;  :api-secret (string)
  ;  :coin (string)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (request-wallet-balance! {:api-key "..." :api-secret "..."})
  ; =>
  ; {:api-key        "..."
  ;  :time-now       "..."
  ;  :uri            "..."
  ;  :wallet-balance {...}}
  ;
  ; @return (map)
  ; {:api-key (string)
  ;  :time-now (string)
  ;  :uri (string)
  ;  :wallet-balance (map)}
  [{:keys [api-key] :as request-props}]
  (let [uri           (wallet.balance.uri/wallet-balance-uri         request-props)
        headers       (wallet.balance.headers/wallet-balance-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.helpers/GET-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (-> {:api-key        api-key
                :uri            uri
                :time-now       (time/epoch-s)
                :wallet-balance (-> response-body :result)}
               (wallet.balance.receive/receive-wallet-balance)))))
