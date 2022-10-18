
# Market order
  Azonnali vétel / eladás az elérhető legjobb piaci áron.

# Limit order
  Vétel / eladás a meghatározott vagy annál kedvezőbb áron.

# Stop order
  Ha a piaci ár eléri a meghatározott mértéket, akkor átvált market order típusra
  és vesz / elad az elérhető legjobb piaci áron.

# Order quantity
  This parameter indicates the quantity of perpetual contracts you want to buy or sell.
  All USD markets have a contract size of 1 USD.

# Good till cancel
  A megbízás visszavonásig, vagy az ügylet teljesüléséig aktív.

# Immediate or cancel
  A megbízás azonnal nem teljesíthető része visszavonásra kerül.

# Fill or kill
  A megbízás vagy teljesül egy meghatározott áron azonnal, vagy visszavonásra kerül.

# Post only
  Post only order will not be executed immediately in the market.
  It will exist as a maker order on the order book, but never match with orders
  that are already on the book.

# Taker fee / maker fee
  When you place an order that gets partially matched immediately,
  you pay a taker fee for that portion.
  The remainder of the order is placed on the order book and, when matched,
  is considered a maker order.
  You pay a maker fee for this remaining portion of the total order.

# Unrealized P&L
  The unrealized P&L is a reflection of what profit or loss could be realized if
  the position were closed at that time.
  The P&L does not become realized until the position is closed.

# Leverage
  Pl. 10x leverage: 1 saját + 9 kölcsön

# Cross / isolated
  Cross:    elérhetővé teszi az account teljes egyenlegét az egyes megbízások számára (NE HASZNÁLD!)
  Isolated: ...

# Positions
  "Sell" - short position
  "Buy"  - long position

# Short position
  A befektető kölcsönvesz a brókertől egy adott részvényt, majd azt eladja a piacon.
  Később a befektető visszavásárolja a részvényt, és visszaadja a brókernek.
  Ha a részvény ára esett, a befektető nyereségre tesz szert.

# Long position
  A befektetők akkor vesznek fel long pozíciót a részvénytőzsdén, amikor részvényeket vásárolnak,
  és tartják a részvényeket abban a reményben, hogy az áruk emelkedni fog.

# Buy
  - Veszel ETH-t, mert azt hiszed, hogy magasabb lesz később az ára
  - Mikor lenn van, akkor BUY

# Sell
  - Eladsz ETH-t, mert azt hiszed, hogy alacsonyabb lesz később az ára és később visszaveszed
  - Mikor fenn van, akkor SELL

# LTP, last traded price
  - Az order-book legújabb bejegyzése szerinti ár.
  - Ne használd conditional order feltételként!

# Mark price (fair price)
  Aktuális piaci ár 6 kriptovaluta tőzsde átlagára alapján kalkulálva.
  Megvéd a pillanatnyi kiugóran magas értékek okozta hibáktól.

# Index price
  TODO ...
