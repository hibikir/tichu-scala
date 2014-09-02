This is a bit of an extended code kata, where I try to implement the rules of Tichu in Scala.

Tichu is an interesting card game that has just enough exceptions and complexities to make using a real type system valuable,
along with having enough computation as to make using generic data structures a major performance hog.
This makes it a pretty interesting game to model in Scala, because of how we can disguise more efficient data structures as
lists and sets that have different internal representations: An entire deck can be represented as a 55 bit field, which
fits just fine in 64 bit registers.