package dao.data

import messaging.serialised.RequestItem

case class Item(id: Option[Int], name: String, price: Int)

object Item {

  def apply(requestItem: RequestItem): Item = Item(None, requestItem.name, requestItem.price)

  def mapperTo(id: Option[Int], name: String, price: Int): Item =
    Item(id: Option[Int], name: String, price: Int)

}

