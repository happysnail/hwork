import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import service.HttpService


object Boot extends App with HttpService{

  override implicit val system = ActorSystem()
  override implicit val materializer = ActorMaterializer()
  override implicit val executor = system.dispatcher
  override val logger = Logging(system, getClass)
  override val config = ConfigFactory.load()

  Http().bindAndHandle(r, config.getString("http.interface"), config.getInt("http.port"))
}