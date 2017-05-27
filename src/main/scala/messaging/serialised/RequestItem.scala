package messaging.serialised

case class RequestItem(name: String, price: Int)

case class ResponseItem(id: Int)

case class RequestOrder(data: String)

case class ResponseOrder(id: Int)