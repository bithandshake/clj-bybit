
(ns bybit-com.position.list.request
    (:require [bybit-com.core.response.errors  :as core.response.errors]
              [bybit-com.core.response.utils   :as core.response.utils]
              [bybit-com.position.list.headers :as position.list.headers]
              [bybit-com.position.list.receive :as position.list.receive]
              [bybit-com.position.list.uri     :as position.list.uri]
              [clj-http.client                 :as clj-http.client]
              [time.api                        :as time]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-position-list!
  ; @important
  ; This function is incomplete and may not behave as expected.
  ;
  ; @description
  ; Fetches the user's position list from the [bybit.com](https://bybit.com) API.
  ;
  ; @param (map) request-props
  ; {:api-key (string)
  ;  :api-secret (string)
  ;  :base-coin (string)(opt)
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
  ; @usage
  ; (request-position-list! {:api-key "..." :api-secret "..." :category "option"})
  ; =>
  ; {:api-key  "..."
  ;  :position-list [...]
  ;  :time-now "..."
  ;  :uri      "..."}
  ;
  ; @return (map)
  ; {:api-key (string)
  ;  :position-list (maps in vector)
  ;  :time-now (string)
  ;  :uri (string)}
  [{:keys [api-key] :as request-props}]
  (let [uri           (position.list.uri/position-list-uri         request-props)
        headers       (position.list.headers/position-list-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.utils/GET-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (-> response-body)
           (-> {:api-key       api-key
                :uri           uri
                :position-list (-> response-body :result :list)
                :time-now      (time/epoch-s)}
               (position.list.receive/receive-position-list)))))
