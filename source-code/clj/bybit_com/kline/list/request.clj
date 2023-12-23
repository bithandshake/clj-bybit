
(ns bybit-com.kline.list.request
    (:require [bybit-com.core.response.errors :as core.response.errors]
              [bybit-com.core.response.utils  :as core.response.utils]
              [bybit-com.kline.list.receive   :as kline.list.receive]
              [bybit-com.kline.list.uri       :as kline.list.uri]
              [clj-http.client                :as clj-http.client]
              [fruits.vector.api              :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-kline-list!
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Fetches market data from the bybit.com API.
  ;
  ; @param (map) request-props
  ; {:interval (string)
  ;   "1m", "3m", "5m", "15m", "30m", "1h", "2h", "4h", "6h", "12h", "1d", "1w"
  ;  :limit (integer)(opt)
  ;   Default: 1000
  ;  :print-status? (boolean)(opt)
  ;  :start (string)(opt)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (request-kline-list! {:interval "1m" :limit 60 :symbol "ETHUSDT"})
  ; =>
  ; {:high 2420 :low 2160 :symbol "ETHUSDT" :time-now "..." :kline-list [{...} {...}] :uri-list ["..." "..."]}
  ;
  ; @return (map)
  ; {:error (namespaced keyword)
  ;  :high (integer)
  ;  :kline-list (maps in vector)
  ;  :low (integer)
  ;  :symbol (string)
  ;  :time-now (integer)
  ;  :uri-list (strings in vector)}
  [{:keys [print-status? symbol] :as request-props}]
  ; The 'api.bybit.com' serves a maximum of 1000 klines per request. Therefore, if the limit
  ; is higher than 1000, this function sends multiple requests.
  (let [{:keys [generated-at uri-list]} (kline.list.uri/kline-list-uri-list request-props)]
       (letfn [(print-f [dex] (if (zero? dex)
                                  (println        "Fetching kline batch:" (inc dex) "of" (count uri-list) "[max 1000 klines / batch]")
                                  (println "\033[1AFetching kline batch:" (inc dex) "of" (count uri-list) "[max 1000 klines / batch]")))

               (f0 [result dex uri] (if print-status? (print-f dex))
                                    (let [response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)
                                          kline-list    (-> response-body :result :list)]
                                         (if-not (core.response.errors/response-body->error? response-body)
                                                 (assoc result :kline-list (vector/concat-items (:kline-list result) kline-list))
                                                 (-> response-body))))]
              (-> (reduce-kv f0 {:symbol symbol :uri-list uri-list :time-now (time.api/epoch-ms->timestamp-string generated-at)} uri-list)
                  (kline.list.receive/receive-kline-list)))))
