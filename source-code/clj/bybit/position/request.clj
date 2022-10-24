
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns bybit.position.request
    (:require [bybit.response.errors  :as response.errors]
              [bybit.response.helpers :as response.helpers]
              [bybit.position.uri     :as position.uri]
              [clj-http.client        :as clj-http.client]
              [mid-fruits.map         :as map]
              [time.api               :as time]))



;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-position-list!
  ; @param (map) request-props
  ;  {:api-key (string)
  ;   :api-secret (string)
  ;   :use-mainnet? (boolean)(opt)
  ;    Default: false}
  ;
  ; @example
  ;  (bybit/request-position-list! {:api-key "..." :api-secret "..."})
  ;  =>
  ;  {:api-key  "..."
  ;   :balance  {:BTC {...} ...}
  ;   :time-now "..."
  ;   :uri      "..."}
  ;
  ; @return (map)
  ;  {:api-key (string)
  ;   :balance (map)
  ;   :time-now (string)
  ;   :uri (string)}
  [{:keys [api-key] :as request-props}]
  (let [api-secret   (get request-props :api-secret)
        timestamp    (time/epoch-ms)
        query-string (str "category=option&symbol=BTC-29JUL22-25000-C")
        param-string (str timestamp api-key 5000 query-string)

        sign1        (server-fruits.hash/hmac-sha256 query-string api-secret)
        sign2        (server-fruits.hash/hmac-sha256 param-string api-secret)
        uri0         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string)
        uri1         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string "&sign="sign1)
        uri2         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string "&sign="sign2)
        uri3         (str "https://api.bybit.com/unified/v3/private/order/list?" param-string "&sign="sign1)
        uri4         (str "https://api.bybit.com/unified/v3/private/order/list?" param-string "&sign="sign2)
        uri5         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string sign1)
        uri6         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string sign2)
        uri7         (str "https://api.bybit.com/unified/v3/private/order/list?" param-string sign1)
        uri8         (str "https://api.bybit.com/unified/v3/private/order/list?" param-string sign2)
        uri9         (str "https://api.bybit.com/unified/v3/private/order/list?" query-string "&" sign1)
        uri10        (str "https://api.bybit.com/unified/v3/private/order/list?" query-string "&" sign2)
        uri11        (str "https://api.bybit.com/unified/v3/private/order/list?" param-string "&" sign1)
        uri12        (str "https://api.bybit.com/unified/v3/private/order/list?" param-string "&" sign2)
        response     (clj-http.client/get uri0
                                          {:headers {"X-BAPI-SIGN-TYPE"   2
                                                     "X-BAPI-SIGN"        sign2
                                                     "X-BAPI-API-KEY"     api-key
                                                     "X-BAPI-TIMESTAMP"   timestamp
                                                     "X-BAPI-RECV-WINDOW" 5000}})
        response-body (response.helpers/GET-response->body response)]
       (if-not (response.errors/response-body->error? response-body)
               (-> response-body
                   (merge {:api-key api-key :uri uri0 :time-now (time/epoch-s)}))
              response-body)))


  ;(let [uri           (position.uri/position-list-uri request-props)
  ;      response-body (-> uri clj-http.client/get response.helpers/GET-response)
  ;     (if-not (response.errors/response-body->invalid-api-details? response-body)
  ;             (-> (map/rekey-item response-body :result :balance)
  ;                 (merge {:api-key api-key :uri uri :time-now (time/epoch-s)})}])
