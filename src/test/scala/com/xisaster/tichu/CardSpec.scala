import com.xisaster.tichu._
import org.scalatest.FunSpec


class CardSpec extends FunSpec{

   it("Card caching makes sure cards are only created once"){
     assert(Card(Jade,Two) == Card(Jade,Two))
     assert(Card(Dog) == Card(Dog))
   }

   it("all cards have different binary representations"){
     val cardMap = Deck().groupBy(_.bitValue)
     assert(cardMap.find(x=> x._2.length>1) == None )
     //the next line lets us do manual inspection to check that our bit representation uses exactly 55 bits.
     //Deck().sortBy(_.bitValue).foreach(x=>println(x  +" " +(Math.log(x.bitValue)/Math.log(2))))
   }

  it("cards share a value with others of the same value or the phoenix"){
    assert(Card(Jade,Two).sharesValue(Card(Sword,Two)))
    assert(!Card(Jade,Two).sharesValue(Card(Sword,Three)))
    assert(Card(Jade,Two).sharesValue(Card(Phoenix)))

  }
}