
(ns bybit.position.list.uri
    (:require [bybit.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn position-list-query-string
  ; @ignore
  ;
  ; @param (map) query-props
  ; {:base-coin (string)(opt)
  ;   Default: "BTC"
  ;   W/ {:category "option"}
  ;  :category (string)
  ;   "linear", "option"
  ;  :cursor (string)(opt)
  ;  :direction (string)(opt)
  ;   "next", "prev"
  ;  :limit (number)(opt)
  ;   Default: 20
  ;   Max: 50
  ;  :symbol (string)(opt)}
  ;
  ; @return (string)
  [{:keys [base-coin category cursor direction limit symbol]}]
  (str "baseCoin="   base-coin
       "&category="  category
       "&cursor="    cursor
       "&direction=" direction
       "&limit="     limit
       "&symbol="    symbol))

(defn position-list-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:base-coin (string)(opt)
  ;   Default: "BTC"
  ;   W/ {:category "option"}
  ;  :category (string)
  ;   "linear", "option"
  ;  :cursor (string)(opt)
  ;  :direction (string)(opt)
  ;   "next", "prev"
  ;  :limit (number)(opt)
  ;   Default: 20
  ;   Max: 50
  ;  :symbol (string)(opt)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (position-list-uri {:category "linear" :symbol "BTC"})
  ; =>
  ; "https://api-testnet.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTC"
  ;
  ; @example
  ; (position-list-uri {:category "linear" :symbol "BTC" :use-mainnet? true})
  ; =>
  ; "https://api.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTC"
  ;
  ; @return (string)
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (position-list-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/position/list?" query-string)))
