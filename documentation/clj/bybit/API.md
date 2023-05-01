
# bybit.api Clojure namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > bybit.api

### Index

- [request-kline-list!](#request-kline-list)

- [request-order-create!](#request-order-create)

- [request-position-list!](#request-position-list)

- [request-quote-ticker!](#request-quote-ticker)

- [request-wallet-balance!](#request-wallet-balance)

### request-kline-list!

```
@param (map) request-props
{:category (string)(opt)
  "inverse", "linear"
 :interval (string)
  "1", "3", "5", "15", "30", "60", "120", "240", "360", "720", "D", "M", "W"
 :limit (integer)
 :start (ms)(opt)
 :symbol (string)
 :use-mainnet? (boolean)(opt)
  Default: false}
```

```
@example
(request-kline-list! {:interval "1" :limit 60 :symbol "ETHUSDT"})
=>
{:high 2420 :low 2160 :symbol "ETHUSDT" :time-now "..." :kline-list [{...} {...}] :uri-list ["..." "..."]}
```

```
@return (map)
{:error (namespaced keyword)
 :high (integer)
 :kline-list (maps in vector)
 :low (integer)
 :symbol (string)
 :time-now (integer)
 :uri-list (strings in vector)}
```

<details>
<summary>Source code</summary>

```
(defn request-kline-list!
  [{:keys [symbol] :as request-props}]
  (let [uri-list  (kline.list.uri/kline-list-uri-list request-props)
        timestamp (time/epoch-ms)]
       (letfn [(print-f [dex] (if (= dex 0)
                                  (println        "Fetching kline batch:" (inc dex) "of" (count uri-list) "[max 200 klines / batch]")
                                  (println "\033[1AFetching kline batch:" (inc dex) "of" (count uri-list) "[max 200 klines / batch]")))

               (f [result dex uri] (if (vector/min? uri-list 2)
                                       (print-f dex))
                                   (let [response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)
                                         kline-list    (-> response-body :result :list)]
                                        (if-not (core.response.errors/response-body->error? response-body)
                                                (assoc result :kline-list (vector/concat-items kline-list (:kline-list result))))))]
              (-> (reduce-kv f {:symbol symbol :uri-list uri-list :time-now timestamp} uri-list)
                  (kline.list.receive/receive-kline-list)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :refer [request-kline-list!]]))

(bybit.api/request-kline-list! ...)
(request-kline-list!           ...)
```

</details>

---

### request-order-create!

```
@param (map) request-props
{:api-key (string)
 :api-secret (string)
 :category (integer)(opt)
  0 (normal), 1 (TP/SL)
  Default: 0
 :order-link-id (string)(opt)
 :order-price (?)(opt)
  When the order type is "Market", the order price is optional.
 :order-type (string)
  "Limit", "Limit_maker", "Market"
 :quantity (?)
 :side (string)
  "Buy", "Sell"
 :smp-type (string)(opt)
  "None", "CancelMaker", "CancelTaker", "CancelBoth"
  Default: "None"
 :symbol (string)
 :time-in-force (string)(opt)
  "GTC" (Good Till Cancel), "FOK" (Fill Or Kill), "IOC" (Immediate Or Cancel)
  Default: "GTC"
 :trigger-price (?)(opt)
 :use-mainnet? (boolean)(opt)
  Default: false}
```

```
@example
(request-order-create! {:api-key "..." :api-secret "..." :side "Buy" :quantity 100 :symbol "ETHUSDT"})
=>
{}
```

```
@return (map)
{}
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
        response-body (core.response.utils/POST-response->body response)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (return response-body))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :refer [request-order-create!]]))

(bybit.api/request-order-create! ...)
(request-order-create!           ...)
```

</details>

---

### request-position-list!

```
@param (map) request-props
{:api-key (string)
 :api-secret (string)
 :base-coin (string)(opt)
  Default: "BTC"
  W/ {:category "option"}
 :category (string)
  "linear", "option"
 :cursor (string)(opt)
 :direction (string)(opt)
  "next", "prev"
 :limit (number)(opt)
  Default: 20
  Max: 50
 :symbol (string)(opt)
 :use-mainnet? (boolean)(opt)
  Default: false}
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
{:api-key (string)
 :position-list (maps in vector)
 :time-now (string)
 :uri (string)}
```

<details>
<summary>Source code</summary>

```
(defn request-position-list!
  [{:keys [api-key] :as request-props}]
  (let [uri           (position.list.uri/position-list-uri         request-props)
        headers       (position.list.headers/position-list-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.utils/GET-response->body response)]
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
(ns my-namespace (:require [bybit.api :refer [request-position-list!]]))

(bybit.api/request-position-list! ...)
(request-position-list!           ...)
```

</details>

---

### request-quote-ticker!

```
@param (map) request-props
{:symbol (string)(opt)}
```

```
@example
(request-quote-ticker! {:symbol "ETHUSDT"})
=>
{}
```

```
@return (map)
{}
```

<details>
<summary>Source code</summary>

```
(defn request-quote-ticker!
  [request-props]
  (let [uri           (quote.ticker.uri/quote-ticker-uri request-props)
        response-body (-> uri clj-http.client/get core.response.utils/GET-response->body)]
       (if (core.response.errors/response-body->error? response-body)
           (return response-body)
           (quote.ticker.receive/receive-quote-ticker response-body))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bybit.api :refer [request-quote-ticker!]]))

(bybit.api/request-quote-ticker! ...)
(request-quote-ticker!           ...)
```

</details>

---

### request-wallet-balance!

```
@param (map) request-props
{:api-key (string)
 :api-secret (string)
 :use-mainnet? (boolean)(opt)
  Default: false}
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
{:api-key (string)
 :time-now (string)
 :uri (string)
 :wallet-balance (map)}
```

<details>
<summary>Source code</summary>

```
(defn request-wallet-balance!
  [{:keys [api-key] :as request-props}]
  (let [uri           (wallet.balance.uri/wallet-balance-uri         request-props)
        headers       (wallet.balance.headers/wallet-balance-headers request-props)
        response      (clj-http.client/get uri {:headers headers})
        response-body (core.response.utils/GET-response->body response)]
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
(ns my-namespace (:require [bybit.api :refer [request-wallet-balance!]]))

(bybit.api/request-wallet-balance! ...)
(request-wallet-balance!           ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

