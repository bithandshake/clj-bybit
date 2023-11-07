
(ns bybit.core.response.utils
    (:require [json.api   :as json]
              [reader.api :as reader]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn time-now->epoch-ms
  ; @ignore
  ;
  ; @param (string) n
  ;
  ; @example
  ; (time-now->epoch-ms "1645550000.123456")
  ; =>
  ; 1645550000123
  ;
  ; @return (ms)
  [n]
  (let [s  (string/before-first-occurence n "." {:return? false})
        ms (string/after-first-occurence  n "." {:return? false})]
       (reader/read-edn (str s (subs ms 0 3)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn GET-response->body
  ; @ignore
  ;
  ; @param (map) response
  ; {:body (string)}
  ;
  ; @example
  ; (GET-response->body {... :body "{\"result\":[{...},{...}]}"})
  ; =>
  ; {:result [{...} {...}]}
  ;
  ; @return (map)
  ; {}
  [{:keys [body]}]
  (-> body reader/read-json json/keywordize-keys json/snake-case-keys))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn POST-response->headers
  ; @ignore
  ;
  ; @param (map) response
  ; {:headers (string)}
  ;
  ; @example
  ; (POST-response->headers {...})
  ; =>
  ; {...}
  ;
  ; @return (map)
  ; {}
  [response]
  (:headers response))

(defn POST-response->body
  ; @ignore
  ;
  ; @param (map) response
  ; {:body (string)}
  ;
  ; @example
  ; (POST-response->body {...})
  ; =>
  ; {...}
  ;
  ; @return (map)
  ; {}
  [{:keys [body]}]
  (-> body reader/read-json json/keywordize-keys json/hyphenize-keys))
