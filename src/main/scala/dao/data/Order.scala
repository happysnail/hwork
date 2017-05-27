package dao.data

import messaging.serialised.RequestOrder

case class Order(id: Option[Int], data: String)

object Order {
  def apply(requestOrder: RequestOrder): Order = Order(None, requestOrder.data)

  def mapperTo(id: Option[Int], data: String): Order =
    Order(id: Option[Int], data: String)

}
