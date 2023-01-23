
(ns bybit.core.response.helpers
    (:require [json.api   :as json]
              [noop.api   :refer [return]]
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
  ; @return (integer)
  [n]
  (let [s  (string/before-first-occurence n "." {:return? false})
        ms (string/after-first-occurence  n "." {:return? false})]
       (reader/read-str (str s (subs ms 0 3)))))

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
  ; XXX#0147
  ; A reader/string->mixed függvény ...
  ; ... nem szereti így: {\"my-key\":\"My value\", ...}
  ; ... így szereti:     {\"my-key\" \"My value\", ...}
  (-> (string/replace-part body #"\":" "\" ")
      (reader/string->mixed)
      (json/keywordize-keys)
      (json/snake-case-keys)))

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
  [{:keys [headers]}]
  (return headers))

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
  ; XXX#0147
  (-> (string/replace-part body #"\":" "\" ")
      (reader/string->mixed)
      (json/keywordize-keys)
      (json/hyphenize-keys)))
