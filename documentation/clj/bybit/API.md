
# <strong>bybit.api</strong> namespace
<p>Documentation of the <strong>bybit/api.clj</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > bybit.api



### API-ADDRESS

<details>
<summary>Source code</summary>

```

```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [API-ADDRESS]]))

(bybit/API-ADDRESS)
(API-ADDRESS)
```

</details>

---

### GET-response->body

```
@param (map) response
```

```
@example
(GET-response->body {... :body "{\"result\":[{...},{...}]}"})
=>
{:result [{...} {...}]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn GET-response->body
  [{:keys [body]}]
  (-> (string/replace-part body #"\":" "\" ")
      (reader/string->mixed)
      (json/keywordize-keys)
      (json/snake-case-keys)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [GET-response->body]]))

(bybit/GET-response->body ...)
(GET-response->body       ...)
```

</details>

---

### POST-response->body

```
@param (map) response
```

```
@example
(POST-response->body {...})
=>
{...}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn POST-response->body
  [{:keys [body]}]
  (-> (string/replace-part body #"\":" "\" ")
      (reader/string->mixed)
      (json/keywordize-keys)
      (json/hyphenize-keys)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [POST-response->body]]))

(bybit/POST-response->body ...)
(POST-response->body       ...)
```

</details>

---

### POST-response->headers

```
@param (map) response
```

```
@example
(POST-response->headers {...})
=>
{...}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn POST-response->headers
  [{:keys [headers]}]
  (return headers))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [POST-response->headers]]))

(bybit/POST-response->headers ...)
(POST-response->headers       ...)
```

</details>

---

### TEST-API-ADDRESS

<details>
<summary>Source code</summary>

```

```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [TEST-API-ADDRESS]]))

(bybit/TEST-API-ADDRESS)
(TEST-API-ADDRESS)
```

</details>

---

### kline-list-uri

```
@param (map) uri-props
```

```
@example
(kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSDT"})
=>
"https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
```

```
@example
(kline-list-uri {:interval "1" :limit 60 :symbol "ETHUSDT" :use-mainnet? true})
=>
"https://api.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=60&start=1646401800000&end=..."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn kline-list-uri
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (kline-list-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/derivatives/v3/public/kline?" query-string)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [kline-list-uri]]))

(bybit/kline-list-uri ...)
(kline-list-uri       ...)
```

</details>

---

### kline-list-uri-list

```
@param (map) uri-props
```

```
@example
(kline-list-uri-list {:interval "1" :limit 240 :symbol "ETHUSDT"})
=>
["https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=40&start=1646401800000&end=..."
 "https://api-testnet.bybit.com/derivatives/v3/public/kline?category=linear&symbol=ETHUSDT&interval=1&limit=200&start=1646404200000&end=..."]
```

```
@return (strings in vector)
```

<details>
<summary>Source code</summary>

```
(defn kline-list-uri-list
  [{:keys [interval] :as uri-props}]
  (letfn [(f [uri-list {:keys [limit] :as uri-props} lap]
             (if (> limit 200)
                 (let [uri-props (merge uri-props {:limit 200 :start (kline.list.helpers/query-start interval (* lap 200))})]
                      (f (cons (kline-list-uri uri-props) uri-list)
                         (assoc uri-props :limit (- limit 200))
                         (inc lap)))
                 (let [uri-props (merge uri-props {:limit limit :start (kline.list.helpers/query-start interval (+ limit (* (dec lap) 200)))})]
                      (cons (kline-list-uri uri-props) uri-list))))]
         (vec (f [] uri-props 1))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [kline-list-uri-list]]))

(bybit/kline-list-uri-list ...)
(kline-list-uri-list       ...)
```

</details>

---

### order-create-uri

```
@param (map) uri-props
```

```
@example
(order-create-uri {})
=>
"https://api-testnet.bybit.com/unified/v3/private/order/create"
```

```
@example
(order-create-uri {:use-mainnet? true})
=>
"https://api.bybit.com/unified/v3/private/order/create"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn order-create-uri
  [{:keys [use-mainnet?] :as uri-props}]
  (let [address (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/order/create")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [order-create-uri]]))

(bybit/order-create-uri ...)
(order-create-uri       ...)
```

</details>

---

### position-list-headers

<details>
<summary>Source code</summary>

```

```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [position-list-headers]]))

(bybit/position-list-headers)
(position-list-headers)
```

</details>

---

### position-list-headers

```
@param (map) uri-props
```

```
@usage
(position-list-headers {:category "linear" :symbol "BTC"})
```

```
@usage
(position-list-headers {:category "linear" :symbol "BTC" :use-mainnet? true})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn position-list-headers
  [headers-props]
  (let [timestamp    (time/epoch-ms)
        param-string (position-list-param-string headers-props timestamp)]
       (core.request.headers/GET-headers headers-props param-string timestamp)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [position-list-headers]]))

(bybit/position-list-headers ...)
(position-list-headers       ...)
```

</details>

---

### position-list-uri

```
@param (map) uri-props
```

```
@example
(position-list-uri {:category "linear" :symbol "BTC"})
=>
"https://api-testnet.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTC"
```

```
@example
(position-list-uri {:category "linear" :symbol "BTC" :use-mainnet? true})
=>
"https://api.bybit.com/unified/v3/private/position/list?category=linear&symbol=BTC"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn position-list-uri
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (position-list-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/position/list?" query-string)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [position-list-uri]]))

(bybit/position-list-uri ...)
(position-list-uri       ...)
```

</details>

---

### request-kline-list!

```
@param (map) request-props
```

```
@example
(request-kline-list! {:interval "1" :limit 60 :symbol "ETHUSDT"})
=>
{:high 2420 :low 2160 :symbol "ETHUSDT" :time-now "..." :kline-list [{...} {...}] :uri-list ["..." "..."]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request-kline-list!
  [{:keys [symbol] :as request-props}]
  (letfn [(f [result uri] (let [response-body (-> uri clj-http.client/get core.response.helpers/GET-response->body)
                                kline-list    (-> response-body :result :list)]
                               (if-not (core.response.errors/response-body->error? response-body)
                                       (assoc result :kline-list (vector/concat-items kline-list (:kline-list result))))))]
         (let [uri-list  (kline.list.uri/kline-list-uri-list request-props)
               timestamp (time/epoch-ms)]
              (-> (reduce f {:symbol symbol :uri-list uri-list :time-now timestamp} uri-list)
                  (kline.list.receive/receive-kline-list)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [request-kline-list!]]))

(bybit/request-kline-list! ...)
(request-kline-list!       ...)
```

</details>

---

### request-order-create!

```
@param (map) request-props
```

```
@example
(request-order-create! {:api-key "..." :api-secret "..." :category "linear" :quantity 100 :symbol "ETHUSDT"})
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request-order-create!
  [{:keys [use-mainnet?] :as request-props}]
  (let [uri           (order.create.uri/order-create-uri               request-props)
        headers       (order.create.headers/order-create-headers       request-props)
        body          (order.create.body/order-create-raw-request-body request-props)
        response      (clj-http.client/post uri {:body body :headers headers})
        response-body (core.response.helpers/POST-response->body response)]
       response-body))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [request-order-create!]]))

(bybit/request-order-create! ...)
(request-order-create!       ...)
```

</details>

---

### request-position-list!

```
@param (map) request-props
```

```
@example
(request-position-list! {:api-key "..." :api-secret "..." :category "option"})
=>
{:api-key  "..."
 :position-list [...]
 :time-now "..."
 :uri      "..."}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request-position-list!
  [{:keys [api-key] :as request-props}]
  (let [uri           (position.list.uri/position-list-uri         request-props)
        headers       (position.list.headers/position-list-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.helpers/GET-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (-> {:api-key       api-key
                :uri           uri
                :position-list (-> response-body :result :list)
                :time-now      (time/epoch-s)}
               (position.list.receive/receive-position-list)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [request-position-list!]]))

(bybit/request-position-list! ...)
(request-position-list!       ...)
```

</details>

---

### request-wallet-balance!

```
@param (map) request-props
```

```
@example
(request-wallet-balance! {:api-key "..." :api-secret "..."})
=>
{:api-key        "..."
 :time-now       "..."
 :uri            "..."
 :wallet-balance {...}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request-wallet-balance!
  [{:keys [api-key] :as request-props}]
  (let [uri           (wallet.balance.uri/wallet-balance-uri         request-props)
        headers       (wallet.balance.headers/wallet-balance-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.helpers/GET-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (-> {:api-key        api-key
                :uri            uri
                :time-now       (time/epoch-s)
                :wallet-balance (-> response-body :result)}
               (wallet.balance.receive/receive-wallet-balance)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [request-wallet-balance!]]))

(bybit/request-wallet-balance! ...)
(request-wallet-balance!       ...)
```

</details>

---

### response-body->error?

```
@param (map) response-body
```

```
@usage
(response-body->error? {...})
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response-body->error?
  [{:keys [ret-code]}]
  (not= ret-code 0))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [response-body->error?]]))

(bybit/response-body->error? ...)
(response-body->error?       ...)
```

</details>

---

### response-body->invalid-api-details?

```
@param (map) response-body
```

```
@usage
(response-body->invalid-api-details? {...})
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response-body->invalid-api-details?
  [{:keys [ret-code]}]
  (or (= ret-code 10003)
      (= ret-code 10004)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [response-body->invalid-api-details?]]))

(bybit/response-body->invalid-api-details? ...)
(response-body->invalid-api-details?       ...)
```

</details>

---

### time-now->epoch-ms

```
@param (string) n
```

```
@example
(time-now->epoch-ms "1645550000.123456")
=>
1645550000123
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn time-now->epoch-ms
  [n]
  (let [s  (string/before-first-occurence n "." {:return? false})
        ms (string/after-first-occurence  n "." {:return? false})]
       (reader/read-str (str s (subs ms 0 3)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [time-now->epoch-ms]]))

(bybit/time-now->epoch-ms ...)
(time-now->epoch-ms       ...)
```

</details>

---

### wallet-balance-headers

```
@param (map) uri-props
```

```
@usage
(wallet-balance-headers {})
```

```
@usage
(wallet-balance-headers {:use-mainnet? true})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn wallet-balance-headers
  [headers-props]
  (let [timestamp    (time/epoch-ms)
        param-string (wallet-balance-param-string headers-props timestamp)]
       (core.request.headers/GET-headers headers-props param-string timestamp)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [wallet-balance-headers]]))

(bybit/wallet-balance-headers ...)
(wallet-balance-headers       ...)
```

</details>

---

### wallet-balance-uri

```
@param (map) uri-props
```

```
@example
(wallet-balance-uri {})
=>
"https://api-testnet.bybit.com/unified/v3/private/account/wallet/balance"
```

```
@example
(wallet-balance-uri {:use-mainnet? true})
=>
"https://api.bybit.com/unified/v3/private/account/wallet/balance"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn wallet-balance-uri
  [{:keys [use-mainnet?] :as uri-props}]
  (let [query-string (wallet-balance-query-string uri-props)
        address      (if use-mainnet? core.uri.config/API-ADDRESS core.uri.config/TEST-API-ADDRESS)]
       (str address "/unified/v3/private/account/wallet/balance?" query-string)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :as bybit :refer [wallet-balance-uri]]))

(bybit/wallet-balance-uri ...)
(wallet-balance-uri       ...)
```

</details>
