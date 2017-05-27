package service

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.circe.generic.auto._
import config.ScalaLearnConfig
import dao.data.ItemAndOrder
import de.heikoseeberger.akkahttpcirce.CirceSupport
import messaging.serialised.{RequestItem, RequestOrder}

import scala.concurrent.ExecutionContextExecutor

trait HttpService extends CirceSupport {

  implicit val actorSystem: ActorSystem
  implicit val executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: ScalaLearnConfig
  val logger: LoggingAdapter

  implicit val orderService: OrderService
  implicit val itemService: ItemService
  implicit val itemAndOrderService: ItemAndOrderService

  val routes: Route = {
    get {
      pathSingleSlash {
        complete("Application")
      } ~
        pathPrefix("show") {
          pathPrefix("orders") {
            pathSuffix("all") {
              complete(orderService.getOrder)
            } ~
            pathSuffix(IntNumber) { number =>
              get {
                rejectEmptyResponse {
                  complete(orderService.getNOrders(number))
                }
              }
            }
          } ~
          pathPrefix("items") {
            pathSuffix("all") {
              complete(itemService.getItems)
            } ~
            pathSuffix(IntNumber) { number =>
              get {
                rejectEmptyResponse {
                  complete(itemService.findFirstNItems(number))
                }
              }
            }
          } ~
          pathPrefix("order") {
            path(IntNumber) { id =>
              get {
                rejectEmptyResponse {
                  complete(orderService.getOrderById(id))
                }
              }
            }
          } ~
          pathPrefix("item") {
            path(IntNumber) { id =>
              get {
                rejectEmptyResponse {
                  complete(itemService.findById(id))
                }
              }
            }
          }
        } ~
        pathPrefix("add") {
          pathPrefix("order") {
            path(Segment) { name =>
              get {
                rejectEmptyResponse {
                  complete(orderService.addOrder(RequestOrder(name)))
                }
              }
            }
          } ~
          pathSuffix("item") {
            complete(itemService.addItem(RequestItem("qwe", 100)))
          }
        }
    } ~
    post {
      path("user") {
        entity(as[RequestOrder]) { request =>
          complete(orderService.addOrder(request))
        }
      }
    }
  }
}