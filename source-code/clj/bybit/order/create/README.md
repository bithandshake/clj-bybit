
### base-price

It will be used to compare with the value of trigger-price, to decide whether
your conditional order will be triggered by crossing trigger price from upper
side or lower side.
Mainly used to identify the expected direction of the current conditional order.

### category

Type of derivatives product: "linear" or "option".

### close-on-trigger?

Close on trigger. To place a closing order, please set it as true, otherwise,
the order might not be placed due to lack of margins

### implied-volatility

For options only; parameters are passed according to the real value;
for example, for 10%, 0.1 is passed.

### order-link-id

User customized order ID.
A max of 36 characters.
A user cannot reuse an orderLinkId, with some exceptions.
Combinations of numbers, letters (upper and lower cases), dashes,
and underscores are supported.
Not required for futures, but required for options.
1. The same order-link-id can be used for both USDC PERP and USDT PERP.
2. An order-link-id can be reused once the original order is either Filled or Cancelled

### order-type

Order price type.

### position-dex

Position mode. Unified margin account is only available in One-Way mode, which is "0".  

### price

Order price.
- Active order:
  https://bybit-exchange.github.io/docs/derivativesV3/unified_margin/###price-price
- Conditional order:
  Must be greater than or equal to 1

### protect-market-maker?

Market maker protection, "true" means this order is a market maker protection order.

### quantity

Order quantity.

### reduce-only

Reduce-Only

### side

Directions: "Buy", "Sell".

### sl-trigger-by

Type of stop-loss activation price, LastPrice by default.

### stop-loss

Stop-loss price, only valid when positions are opened.

### symbol

Name of Contract

### take-profit

Take-profit price, only valid when positions are opened.

### time-in-force

Time in force.

### tp-trigger-by

Type of take-profit activation price, LastPrice by default.

### trigger-by

Trigger price type: Market price / Mark price.  

### trigger-price

If you're expecting the price to rise to trigger your conditional order, make sure
trigger-price > max(market price, base-price) else,
trigger-price < min(market price, base-price)
