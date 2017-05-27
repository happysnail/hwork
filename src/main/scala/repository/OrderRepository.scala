package repository

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import dao.data.Order
import dao.tables.OrderTable
import service.DatabaseService

import scala.concurrent.Future

abstract class OrderRepository extends OrderTable{

  protected val databaseService: DatabaseService

  import databaseService._
  import databaseService.drive.api._

  def getAllOrders: Future[Seq[Order]] =
    db.run(orderTable.result)

  def delete(id: Int): Future[Int] =
    db.run(orderTable.filter(_.id === id).delete)


}
