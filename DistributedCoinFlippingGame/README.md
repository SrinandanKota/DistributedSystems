# DistributedSystems - Distributed Coin Flipping Game Using Stellar

I. OVERVIEW

The objective of this course project is to use Stellar to build a Distributed Online Coin Flipping Game, which allows two or more
participants to play the game fairly and transfer funds to each other safely. Stellar is an open platform for building financial products
that connect people everywhere. The goal of Stellar is to achieve fast, reliable and near-zero-cost transfers. By using the services
provided by the stellar platform, we can build adistributed payment system to support online Coin Flipping game.

II. PROJECT REQUIREMENT

Phase I project aims to develop a distributed payment system using Stellar, which allows users to send and receive funds between or
among each other; Phase II project aims to develop a simple two-party online Coin Flipping game.

III. PHASE I

The first phase of the project is intended to familiarize you with the Stellar platform and learn the process of building user accounts.
Most distributed applications interact with the Stellar network through Horizon, a RESTful HTTP API server. The detailed information of
Horizon can be learned by following the links in Section V. This phase will create two accounts, with which users transfer lumens
(funds) to each other. 

The basic requirements which were met are:

1. Once the account is created, a new User Interface will pop up to allow user to query the account ID and its balance through that user
interface.
2. If the user wishes to initiate a transfer to the targeted account, she can enter the address/accountID and transfer some amount of
funds to the targeted account.
3. When user wants to check the activity history, the system can provide the transaction records within a day.
4. Errors, server exceptions, and invalid user input shall produce reasonably informative error descriptions to the user.
5. Learn the APIs that allow you to program with Stellar.

IV. PHASE II

This phase implements a two-party distributed Coin Flipping game. We start by a simplified coin flipping" game: Alice and Bob want to
bet ten dollars. They both agree to the bet ahead of time and the method for determining the winner. Bob will flip a coin in the air,
and while it is rotating Alice calls out "Head" or "Tail". When the coin lands, they both immediately have a clear understanding of
who won the bet, and they both have assurance that the outcome was random and that neither of them was able to influence the outcome.

One shortcoming is that the sequence of steps in this ceremony requires that both parties have to be present at the same place at the
same time. Also, both parties still have to trust that whoever loses will pay up. In this phase, we would like to be able to have an
on-line coin  flipping game that is not only just as "fair", but also the problem of making sure that the loser actually pays, with the
support of implementation of Stellar.

The first challenge is replacing the "coin flip" mechanism with some online equivalent. Let's say we now have two parties, Alice and Bob, who all want to select a number with equal probability. Here's one attempt at such a
protocol. Each of them picks a random number, e.g. Alice chooses 0, Bob chooses 1. They tell each other their numbers, and they combine
the output as val = 00; 01; 10 or 11. Alice (first participant) wins if val = 00 or 11 (i.e., two numbers are the same). Otherwise, Bob
(second participant) wins (if val = 01 or 10 (i.e., two numbers are different)). If both of them chose their random numbers independently
, this would indeed work. But remember that we are doing this over the Internet, and there is no way to ensure that they all send their
numbers "simultaneously". Alice might wait until she hears Bob's numbers before broadcasting hers. If she does this, you can see how it
is trivial for her to make the final output be whatever she wants. We cannot design the protocol to convince every party involved that
none of the other parties can cheat. To solve this problem, one possible solution is that we can use hash commitments by following the
two-round protocol below:

Round 1: Each participant chooses a random number by calling appropriate random number generation function (most program languages like
C and Java provide such functions). For example, Alice first picks x and Bob picks y, independently. Then each participant feeds her/his
number to a hash function and gets the corresponding hash value, e.g., H(x) and H(y). After that, the hash value and her bet (e.g., 20
lumens) should be sent to an independent 3rd-party banker. The banker will collect and keep the hash values and the bets deposited from
each participant as well as the transaction time. 

Round 2: The two parties reveal their values, x and y, to each other and the 3rd-party banker. If both x and y revealed by the
participants are consistent with the committed H(x) and H(y) in Round 1, respectively, the two parties and the banker can each decide
who is the winner of the game by calculating ((x+y) mod 2). If the result is 0, Alice is the winner. Otherwise, Bob is the winner. Doing
so will guarantee no one can cheat, assuming we use a reliable hash function. At the end of the game, 95% of the deposits from both
parties will be sent to the winner from the banker, the remaining 5% will be retained by the banker as a service fee. This round of the
game will end. Such game can be repeated many many rounds.

Some of the requirements:

1. Implement the banker program that can store and update the game transactions. When the banker wants to query the activity history, the
system should provide the game transaction records within one day.
2. There are two user roles: participants and banker. A separate User Interface is required to each role/particpant. Only one active game
at a time needs to be supported. At any given time, there may be at most one banker and at least two participants active with respect to
a given round of game.

V. Useful links

1. A short tutorial on Stellar system: https : //www.stellar.org/developers/guides/get - started/index.html
2. Stellar javadoc is available at: https : //stellar.github.io/java - stellar - sdk/
3. Stellar laboratory: https : //www.stellar.org/
