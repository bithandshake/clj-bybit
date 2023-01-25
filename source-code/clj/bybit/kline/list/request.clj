
(ns bybit.kline.list.request
    (:require [bybit.core.response.errors :as core.response.errors]
              [bybit.core.response.utils  :as core.response.utils]
              [bybit.kline.list.errors    :as kline.list.errors]
              [bybit.kline.list.receive   :as kline.list.receive]
              [bybit.kline.list.uri       :as kline.list.uri]
              [bybit.kline.list.utils     :as kline.list.utils]
              [clj-http.client            :as clj-http.client]
              [time.api                   :as time]
              [vector.api                 :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-kline-list!
  ; @param (map) request-props
  ; {:category (string)(opt)
  ;   "inverse", "linear"
  ;  :interval (string)
  ;   "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;  :limit (integer)
  ;  :start (ms)(opt)
  ;  :symbol (string)
  ;  :use-mainnet? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (request-kline-list! {:interval "1" :limit 60 :symbol "ETHUSDT"})
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
  [{:keys [symbol] :as request-props}]
  ; Az api.bybit.com szerver által elfogadott maximális limit érték 200, ezért az annál több
  ; periódust igénylő lekéréseket több részletben küldi el, majd ... dolgozza fel a válaszokat.
  (letfn [(f [result uri] (let [response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)
                                kline-list    (-> response-body :result :list)]
                               (if-not (core.response.errors/response-body->error? response-body)
                                       (assoc result :kline-list (vector/concat-items kline-list (:kline-list result))))))]
         (let [uri-list  (kline.list.uri/kline-list-uri-list request-props)
               timestamp (time/epoch-ms)]
              (-> (reduce f {:symbol symbol :uri-list uri-list :time-now timestamp} uri-list)
                  (kline.list.receive/receive-kline-list)))))
                 ;(kline.list.errors/kline-list-data<-error request-props)
