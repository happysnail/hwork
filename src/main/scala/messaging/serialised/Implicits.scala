package messaging.serialised

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

object Implicits  extends SprayJsonSupport with DefaultJsonProtocol {
  //implicit val itemFormat = jsonFormat2(Item)
  //implicit val orderFormat = jsonFormat1(Order)
}
