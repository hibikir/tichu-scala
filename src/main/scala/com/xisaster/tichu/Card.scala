package com.xisaster.tichu


object Card {

   val allCards : List[Card]= {
    val cards = for (value <- CardValue.Suited;
                     suit <- Suit.values) yield new SuitedCard(suit, value)
    val special = for (value <- CardValue.Special) yield new SpecialCard(value)
    cards:::special
  }

  private val cache =allCards.groupBy {
    case x: SuitedCard => (x.suit, x.value)
    case x: SpecialCard => (null, x.value)
  }.mapValues(_.head)

  private val bitCache =allCards.groupBy(_.bitValue).mapValues(_.head)

  def apply(bitValue:Long):Card = bitCache.get(bitValue).get

  def apply(suit: Suit, value: SuitedValue): Card = {
    cache.get((suit, value)).get
  }

  def apply(value: SpecialValue): Card = {
    cache.get((null, value)).get
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