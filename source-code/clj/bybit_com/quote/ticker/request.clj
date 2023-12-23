
(ns bybit-com.quote.ticker.request
    (:require [bybit-com.core.response.errors :as core.response.errors]
              [bybit-com.core.response.utils  :as core.response.utils]
              [bybit-com.quote.ticker.receive :as quote.ticker.receive]
              [bybit-com.quote.ticker.uri     :as quote.ticker.uri]
              [clj-http.client                :as clj-http.client]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-quote-ticker!
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Fetches a quote ticker from the bybit.com API.
  ;
  ; @param (map) request-props
  ; {:symbol (string)(opt)}
  ;
  ; @usage
  ; (request-quote-ticker! {:symbol "ETHUSDT"})
  ; =>
  ; {}
  ;
  ; @return (map)
  ; {}
  [request-props]
  (let [uri           (-> request-props quote.ticker.uri/quote-ticker-uri)
        response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)]
       (if (-> response-body core.response.errors/response-body->error?)
           (-> response-body)
           (-> response-body quote.ticker.receive/receive-quote-ticker))))
