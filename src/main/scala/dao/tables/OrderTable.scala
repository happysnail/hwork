package dao.tables

import dao.data. Order
import service.DatabaseService
import slick.lifted.ProvenShape

import scala.concurrent.Future

trait OrderTable {

  protected val databaseService: DatabaseService
  import databaseService._
  import databaseService.drive.api._

  class Orders(tag: Tag) extends Table[Order](tag, "orders") {
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    val data: Rep[String] = column[String]("data")

    def * : ProvenShape[Order] = (id, data) <> ((Order.mapperTo _).tupled, Order.unapply)
  }

  val orderTable = TableQuery[Orders]

  def orderById(id: Int): DBIO[Option[Order]] =
    orderTable.filter(_.id === id).result.headOption

  def selectAllOrders: Future[Seq[Order]] =
    db.run(orderTable.result)

  def getFirstN(number: Int): DBIO[Seq[Order]] =
    orderTable.take(number).result

  def getNOrders(number: Int, start: Int): DBIO[Seq[Order]] =
    orderTable.drop(start).take(number).result

  def findOrderById(id: Int): Future[Option[Order]] =
    db.run(orderById(id))

  def findFirstN(number: Int): Future[Seq[Order]] =
    db.run(getFirstN(number))

  def findNOrders(number: Int, start: Int): Future[Seq[Order]] =
    db.run(getNOrders(number,start))

  def insertOrder(order: Order): Future[Order] =
    db.run(orderTable returning orderTable += order)

  def allOrders: Future[List[Order]] =
    db.run(orderTable.to[List].result)

}
