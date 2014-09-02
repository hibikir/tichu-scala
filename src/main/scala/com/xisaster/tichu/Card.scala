package com.xisaster.tichu


object Card {
  //this could be precalculated to avoid having a mutable map
  private val cache = scala.collection.mutable.Map[(Suit,CardValue), Card]()

  def apply(suit: Suit, value: SuitedValue): Card = {
    cache.get((suit, value)) match {
      case Some(x) => x
      case _ =>
        val card = new SuitedCard(suit, value)
        cache.put((card.suit,card.value),card)
        card
    }
  }

  def apply(value: SpecialValue): Card = {
    cache.get((null, value)) match {
      case Some(x) => x
      case _ =>
        val card = new SpecialCard(value)
        cache.put((null,card.value),card)
        card
    }
  }
}

abstract class Card( val value: CardValue) {

  val bitValue:Long

  def follows(card: Card): Boolean = value.follows(card.value)


  def follows2(card: Card): Boolean = value.follows2(card.value)

  def >(op: Card): Boolean = {
    op.value match {
      case v if v.value > value.value => true
      case _ =>false
    }
  }

  //this 'share' is one of the uglyness that comes from the phoenix's rules
  //From some perspectives, the phoenix shares a value with all numbers, from others,
  //it never shares a value with anything
  def sharesValue(card: Card): Boolean = {
    if (value == Phoenix) return !CardValue.Special.contains(card.value)
    card.value match {
      case `value` => true
      case Phoenix => !CardValue.Special.contains(value)
      case _ => false
    }
  }

}

class SpecialCard(value: SpecialValue) extends Card(value) {
  override val bitValue: Long = {
    value match{
      case Dog => 1L <<(13*4)
      case Dragon => 1L <<(13*4 +1)
      case Mahjong => 1L <<(13*4 +2)
      case Phoenix => 1L <<(13*4 +3)

    }
  }

  override def toString: String = value.toString
}

class SuitedCard(val suit: Suit,
                 override val value: CardValue) extends Card(value){
  override val bitValue: Long = 1l <<(13*suit.bitOffset + (value.value-2))

  override def toString: String = suit.shortName + value.toString
}