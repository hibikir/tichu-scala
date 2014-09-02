package com.xisaster.tichu

sealed trait Suit {
  val name:String
  val shortName:String
  val bitOffset:Int
  override def toString = name
}

object Suit{
  val values = List(Jade,Sword,Pagoda,Star)
}

object Jade extends Suit{
  val name = "Jade"
  val shortName = "G"
  val bitOffset = 0
}

object Sword extends Suit{
  val name = "Sword"
  val shortName = "B"
  val bitOffset = 1
}

object Pagoda extends Suit{
  val name = "Pagoda"
  val shortName = "U"
  val bitOffset = 2
}

object Star extends Suit{
  val name = "Star"
  val shortName = "R"
  val bitOffset = 3
}
