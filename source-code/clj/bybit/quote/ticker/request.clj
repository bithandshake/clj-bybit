
(ns bybit.quote.ticker.request
    (:require [bybit.core.response.errors :as core.response.errors]
              [bybit.core.response.utils  :as core.response.utils]
              [bybit.quote.ticker.receive :as quote.ticker.receive]
              [bybit.quote.ticker.uri     :as quote.ticker.uri]
              [clj-http.client            :as clj-http.client]
              [noop.api                   :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-quote-ticker!
  ; @ignore
  ;
  ; @param (map) request-props
  ; {:symbol (string)(opt)}
  ;
  ; @example
  ; (request-quote-ticker! {:symbol "ETHUSDT"})
  ; =>
  ; {}
  ;
  ; @return (map)
  ; {}
  [request-props]
  (let [uri           (quote.ticker.uri/quote-ticker-uri request-props)
        response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (quote.ticker.receive/receive-quote-ticker response-body))))
