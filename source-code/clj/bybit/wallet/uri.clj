
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.wallet.uri
    (:require [bybit.request.sign :as request.sign]
              [bybit.uri.config   :as uri.config]
              [time.api           :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wallet-balance-uri
  ; @param (map) uri-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/wallet-balance-uri {:api-key "..." :api-secret "..."})
  ;  =>
  ; https://api-testnet.bybit.com/v2/private/wallet/balance?api_key={api_key}&timestamp={timestamp}&sign={sign}
  ;
  ; @example
  ;  (bybit/wallet-balance-uri {:api-key "..." :api-secret "..." :use-mainnet? true})
  ;  =>
  ; https://api.bybit.com/v2/private/wallet/balance?api_key={api_key}&timestamp={timestamp}&sign={sign}
  ;
  ; @return (string)
  [{:keys [api-key api-secret use-mainnet?]}]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [timestamp    (time/epoch-ms)
        query-string (str "api_key=" api-key "&timestamp=" timestamp)
        address      (if use-mainnet? uri.config/PRIVATE-API-ADDRESS uri.config/PRIVATE-TEST-API-ADDRESS)]
       (str address "/wallet/balance?" (request.sign/signed-query-string query-string api-secret))))
