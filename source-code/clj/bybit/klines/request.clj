
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.klines.request
    (:require [bybit.klines.errors    :as klines.errors]
              [bybit.klines.helpers   :as klines.helpers]
              [bybit.klines.uri       :as klines.uri]
              [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [clj-http.client        :as clj-http.client]
              [mid-fruits.vector      :as vector]
              [time.api               :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-kline-list!
  ; @param (map) request-props
  ;  {:interval (string)
  ;    "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
  ;   :limit (integer)
  ;   :symbol (string)
  ;    "BTCUSD", "ETHUSD"
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/request-kline-list! {:interval "1" :limit 60 :symbol "ETHUSD"})
  ;  =>
  ;  {:high 2420 :low 2160 :symbol "ETHUSD" :time-now "..." :kline-list [{...} {...}] :uri-list ["..." "..."]}
  ;
  ; @return (map)
  ;  {:error (namespaced keyword)
  ;   :high (integer)
  ;   :kline-list (maps in vector)
  ;   :low (integer)
  ;   :symbol (string)
  ;   :time-now (integer)
  ;   :uri-list (strings in vector)}
  [{:keys [symbol] :as request-props}]
  ; Az api.bybit.com szerver által elfogadott maximális limit érték 200, ezért az annál több
  ; periódust igénylő lekéréseket több részletben küldi el, majd ... dolgozza fel a válaszokat.
  (letfn [(f [result uri] (let [response-body (-> uri clj-http.client/get response.helpers/GET-response->body)
                                kline-list    (-> response-body :result)]
                               (if-not (response.errors/response-body->error? response-body)
                                       (-> result (update :kline-list vector/concat-items kline-list)))))]
         (let [uri-list        (klines.uri/kline-list-uri-list request-props)
               kline-list-data {:symbol symbol :uri-list uri-list :time-now (time/epoch-s)}]
              (-> (reduce f kline-list-data uri-list)
                  (klines.helpers/receive-kline-list-data)
                  (klines.errors/kline-list-data<-error request-props)))))
