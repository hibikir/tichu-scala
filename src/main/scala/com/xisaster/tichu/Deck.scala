package com.xisaster.tichu


object Deck {
  def apply(): List[Card] = deck

  private val deck : List[Card]= {
    val cards = for (value <- CardValue.Suited;
                     suit <- Suit.values) yield Card(suit, value)
    val special = for (value <- CardValue.Special) yield Card(value)
    cards:::special
  }

}