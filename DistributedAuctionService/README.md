# Distributed Auction Service

I. OVERVIEW

The objective of this machine problem is to use CORBA to build a Distributed Auction Service, which allows the buying and
selling of individual items, using an English auction protocol (increasing price, current price visible to all parties). Optionally,
other auction protocols could be supported, such as Dutch (the public price is decreased until someone bids) or some variant of
sealed-bid (each interested buyer submits one bid, all bids are considered at once, prices are not public, and the bidder making
the highest bid would win the object being sold for either the highest price–a first-price auction–or the second-highest–a second
price (also called Vickrey auction)). The idea for this project is from Ebay and some course projects in other universities.

II. PHASE I - A SIMPLE AUCTION SERVER

The first phase of the project needs us to build and deploy a simple distributed CORBA-based application. For this phase we develop a
single item distributed auction server, where a seller can offer an item to be sold, and bidders can bid on the item. No other items are
auctioned while the current auction is active. The auction server will keep track of the highest bidder and accordingly update the item
price. At any point, the seller may decide to sell the item, in which case the auction server will be ready for another item to be 
auctioned by a seller. More specifically, two Java applications are required, a server and a client. Any number of clients may connect
to the server.

The system has the following general requirements:

1. There are two user roles: Seller and Bidder. A separate User Interface is required for each role. Only one active auction at
a time needs to be supported. At any given time, there may be at most one seller and any number of bidders active with
respect to a given auction server. However, the server must not lose information even if clients are repeatedly stopped
and restarted.
2. The Client may be built as a single application that supports both Seller and Bidder interfaces, or two separate applications
may be built, one for a Seller and another for a Bidder.
3. Errors, server exceptions, and invalid user input shall produce reasonably informative error displays to the user.
4. Note that for this phase of the project, a single IDL interface Auction is sufficient. This interface should contain the
exception declarations, attribute declarations, and all of the interfaces for the operations that specify the functionality described
for the Seller and the Bidder.
5. Client and Server shall be deployed on at least two different machines. It is acceptable for the client to be replicated
on additional machines. Client and Server shall not operate out of the same directory. The Client environment shall not
contain unnecessary Server components.
6. The Seller and Bidder roles should provide the functionality specified by the following use case scenarios:

– Offer-Item: If the auction is currently empty, any user may offer an item for sale by providing a user ID, an item
description, and an optional initial price. The initial price becomes the current price. This operation is not permitted
if the auction is currently busy.

– Sell: If at least one Bid has been accepted, the Seller may sell the item at the current price. The actual sales transaction
is assumed to take place outside the scope of the Auction. The Seller must identify itself by providing the same
userID that was used in the offer-item operation.

– Bid: Any user, other than the seller, may place a bid on the current item by supplying a user ID and a bid price. If
the price bid is higher than the current price, the bidder becomes the current high bidder, and the price becomes the
current price.

– view-high-bidder: The identity of the current high bidder, if any, is viewable only to the current Seller.

– view-bid-status: A bidder can determine at any time whether it is the current high bidder. This operation will fail if
the auction is not currently active (no item is currently for sale, or the current item is already sold).

– view-auction-status: Any user may view the status of the auction. Status includes auction state (empty/active), and
if the auction is active, the description of current item and current price. The identity of the current high bidder is
viewable only to the seller.

The files included in this folder have met the design requirements for the Auction Server that has been mentioned here.
