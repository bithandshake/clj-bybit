
# clj-bybit

### Overview

The <strong>clj-bybit</strong> is a set of Clojure functions that help you to connect
to the Bybit.com API v3.

### Warning

The <strong>clj-bybit</strong> library is in pre-beta stage.
Not recommended to use in product releases!

### Documentation

The <strong>clj-bybit</strong> functional documentation is [available here](documentation/COVER.md).

# Usage

> Some parameters of the following functions and some other functions won't be discussed.
  To learn more about the available functionality, check out the
  [functional documentation](documentation/COVER.md)!

> All the following functions take the {:use-mainnet? ...} setting, which provides
  you the choice of whether the `api-testnet.bybit.com` or the `api.bybit.com`
  will be targeted.

> Some of the following functions requires to get an API key from <strong>bybit.com</strong>.

### Index

- [How to get market data?](#how-to-get-market-data)

- [How to create an order?](#how-to-create-an-order)

- [How to get your position list?](#how-to-get-your-position-list)

- [How to get your wallet balance?](#how-to-get-your-wallet-balance)

### How to get market data?

The [`bybit.api/request-kline-list!`](documentation/clj/bybit/API.md#request-kline-list)
function requests market data from <strong>bybit.com</strong>.

In the following example the function requests the latest 60 1 minute long period's
data from the ETH/USDT market.

```
(request-kline-list! {:interval "1m" :limit 60 :symbol "ETHUSDT"})
```

### How to create an order?

The [`bybit.api/request-order-create!`](documentation/clj/bybit/API.md#request-order-create)
function sends your market order to <strong>bybit.com</strong>.

> Warning!
  This function is not completed yet! It will be available in later versions!

### How to get your position list?

The [`bybit.api/request-position-list!`](documentation/clj/bybit/API.md#request-position-list)
function requests your position list from <strong>bybit.com</strong>.

> Warning!
  This function is not completed yet! It will be available in later versions!

### How to get your wallet balance?

The [`bybit.api/request-wallet-balance!`](documentation/clj/bybit/API.md#request-wallet-balance)
function requests your wallet balance from <strong>bybit.com</strong>.

> Warning!
  This function is not completed yet! It will be available in later versions!
