
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.position.uri
    (:require [bybit.request.sign :as request.sign]
              [bybit.uri.config   :as uri.config]
              [time.api           :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-list-uri
  ; @param (map) uri-props
  ;  {:base-coin (string)(opt)
  ;    Default: "BTC"
  ;    W/ {:category "option"}
  ;   :category (string)
  ;    "linear", "option"
  ;   :cursor (string)(opt)
  ;   :direction (string)(opt)
  ;    "next", "prev"
  ;   :limit (number)(opt)
  ;    Default: 20
  ;    Max: 50
  ;   :symbol (string)(opt)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/position-list-uri {:category "linear" :symbol "BTCUSDT"})
  ;  =>
  ; https://api-testnet.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTCUSDT&timestamp={timestamp}&sign={sign}
  ;
  ; @example
  ;  (bybit/position-list-uri {:category "linear" :symbol "BTCUSDT" :use-mainnet? true})
  ;  =>
  ; https://api.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTCUSDT&timestamp={timestamp}&sign={sign}
  ;
  ; @return (string)
  [{:keys [api-key api-secret use-mainnet?]}]
  ; Please make sure that your timestamp is in sync with our (bybit.com) server time.
  ; You can use the Server Time endpoint.
  (let [timestamp    (time/epoch-ms)
        query-string (str "category=option&api_key=" api-key "&timestamp=" timestamp)
        address      (if use-mainnet? uri.config/API-ADDRESS uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/position/list?" (request.sign/signed-query-string query-string api-secret))))
