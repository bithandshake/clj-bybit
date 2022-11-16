
# <strong>bybit.api</strong> namespace
<p>Documentation of the <strong>bybit/api.clj</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > bybit.api



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
