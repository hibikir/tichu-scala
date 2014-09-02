package com.xisaster.tichu


object CardValue {
  val Suited = List(Two,Three,Four,Five,Six,Seven,Eight,Nine,Ten,Jack,Queen,King,Ace)
  val Special = List(Mahjong,Dog,Dragon,Phoenix)
  def withName(s:String):CardValue = Suited.find(x=>x.toString ==s).get
  implicit val ordering:Ordering[CardValue] = Ordering.by(c=>c.value)
}

sealed trait CardValue{
  def follows(card: CardValue): Boolean
  def follows2(card: CardValue): Boolean
  def scoreOnPile : Int = 0
  def value :Int
  val offset = value -2
  def < (other:CardValue):Boolean = value < other.value
  def > (other:CardValue):Boolean = value > other.value
}

sealed trait SuitedValue extends CardValue{
  def follows(card: CardValue): Boolean = card match {
    case Dog => false
    case Dragon => false
    // case CardValue.Mahjong => false;
    case Phoenix => true
    case v if value - v.value == 1 => true
    case _ => false
  }
  def follows2(card: CardValue): Boolean = card match {
    case Dog => false
    case Dragon => false
    // case CardValue.Mahjong => false;
    case Phoenix => true
    case v if value - v.value == 2 => true
    case _ => false
  }

  override def toString:String = value.toString
}

object Two extends SuitedValue{
  val value = 2
}

object Three extends SuitedValue{
  val value = 3
}

object Four extends SuitedValue{
  val value = 4
}

object Five extends SuitedValue{
  val value = 5
  override def scoreOnPile = 5
}

object Six extends SuitedValue{
  val value = 6
}

object Seven extends SuitedValue{
  val value = 7
}

object Eight extends SuitedValue{
  val value = 8
}

object Nine extends SuitedValue{
  val value = 9
}

object Ten extends SuitedValue{
  val value = 10
  override def scoreOnPile = 10

}

object Jack extends SuitedValue{
  val value = 11
  override def toString:String = "J"

}

object Queen extends SuitedValue{
  val value = 12
  override def toString:String = "Q"
}

object King extends SuitedValue{
  val value = 13
  override def scoreOnPile = 10
  override def toString:String = "K"
}

object Ace extends SuitedValue{
  val value = 14
  override def toString:String = "A"
}


trait SpecialValue extends CardValue{
  def follows(card: CardValue): Boolean  = false
  def follows2(card: CardValue): Boolean = false
}

object Dog extends SpecialValue{
  def value:Int  = 0
  override def toString :String = "DG"
}


object Mahjong extends SpecialValue{
  def value:Int  = 1
  override def toString :String = "MJ"
}

object Dragon extends SpecialValue{
  def value:Int  = 16
  override def toString :String = "DR"
  override def scoreOnPile : Int = 25

}

object Phoenix extends SpecialValue{
  override def follows(card: CardValue): Boolean  = card match {
    case c:SpecialValue => false
    case Ace => false //No Card Higher than A
    case _ => true
  }
  override def follows2(card: CardValue): Boolean = card match {
    case c:SpecialValue => false
    case Ace => false //No Card Higher than A
    case King => false
    case _ => true
  }
  def value:Int  = 15
  override def scoreOnPile : Int = -25
  override def toString :String = "PH"
}


