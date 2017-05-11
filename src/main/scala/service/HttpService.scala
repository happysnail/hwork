package service

import akka.NotUsed
import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.Multipart.FormData
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.typesafe.config.Config
import models.{Item, Order}
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContextExecutor
import scala.util.Random

trait HttpService extends Directives with JsonSupport{

  implicit val system: ActorSystem

  implicit def executor: ExecutionContextExecutor

  implicit val materializer: Materializer

  def config: Config

  val logger: LoggingAdapter

  val numbers: Source[Int, NotUsed] = Source.fromIterator(() =>
    Iterator.continually(Random.nextInt()))

  val routes: Route =
    path("random") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/plain(UTF-8)`,
            // transform each number to a chunk of bytes
            numbers.map(n => ByteString(s"$n\n"))
          )
        )
      }
    }
  val r =
  get {
    pathSingleSlash {
      complete(Item("asd", 42)) // will render as JSON
    }
  } ~
    post {
      entity(as[Order]) { order => // will unmarshal JSON to Order
        val itemsCount = order.items.size
        val itemNames = order.items.map(_.name).mkString(", ")
        complete(s"Ordered $itemsCount items: $itemNames")
      }
    }

//
////  val routess = {
////    logRequestResult("scala-learn2") {
////      pathPrefix("ip") {
////        (get & path(Segment)) { ip =>
////          complete {
////            fetchIpInfo(ip).map[ToResponseMarshallable] {
////              case Right(ipInfo) => ipInfo
////              case Left(errorMessage) => BadRequest -> errorMessage
////            }
////          }
////        } ~
////          (post & entity(as[IpPairSummaryRequest])) { ipPairSummaryRequest =>
////            complete {
////              val ip1InfoFuture = fetchIpInfo(ipPairSummaryRequest.ip1)
////              val ip2InfoFuture = fetchIpInfo(ipPairSummaryRequest.ip2)
////              ip1InfoFuture.zip(ip2InfoFuture).map[ToResponseMarshallable] {
////                case (Right(info1), Right(info2)) => IpPairSummary(info1, info2)
////                case (Left(errorMessage), _) => BadRequest -> errorMessage
////                case (_, Left(errorMessage)) => BadRequest -> errorMessage
////              }
////            }
////          }
////      }
////    }
////  }


}