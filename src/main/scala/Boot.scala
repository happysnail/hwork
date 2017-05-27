import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import config.ScalaLearnConfig
import dao.data.{Item, ItemAndOrder}
import service._

import scala.util.{Failure, Success, Try}

object Boot extends App with HttpService {
  override implicit val actorSystem = ActorSystem()
  override implicit val materializer = ActorMaterializer()
  override implicit val executor = actorSystem.dispatcher
  override val logger = Logging(actorSystem, getClass)

  val config = new ScalaLearnConfig()

  implicit val databaseService: Try[DatabaseService] = DatabaseService(config)

  override implicit val orderService: OrderService = new OrderService(databaseService.get)
  override implicit val itemService: ItemService = new ItemService(databaseService.get)
  override implicit val itemAndOrderService: ItemAndOrderService = new ItemAndOrderService(databaseService.get)

  Http().bindAndHandle(routes, config.httpHost, config.httpPort) onComplete {
    case Success(message) =>
      logger.debug("Server started Successfully")
    case Failure(err) =>
      logger.error(err, "Server did not start")
      System.exit(-1)
  }

  val v = Item(None, "asd", 10)
}