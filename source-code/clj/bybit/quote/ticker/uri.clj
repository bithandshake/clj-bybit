
(ns bybit.quote.ticker.uri
    (:require [bybit.core.uri.config :as core.uri.config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn quote-ticker-query-string
  ; @ignore
  ;
  ; @param (map) query-props
  ; {:symbol (string)(opt)}
  ;
  ; @return (string)
  [{:keys [symbol]}]
  (str "symbol=" symbol))

(defn quote-ticker-uri
  ; @ignore
  ;
  ; @param (map) uri-props
  ; {:symbol (string)(opt)}
  ;
  ; @example
  ; (quote-ticker-uri {})
  ; =>
  ; "https://api.bybit.com/spot/v3/public/quote/ticker/24hr"
  ;
  ; @return (string)
  [uri-props]
  (let [query-string (quote-ticker-query-string uri-props)]
       (str core.uri.config/API-ADDRESS "/spot/v3/public/quote/ticker/24hr?" query-string)))
