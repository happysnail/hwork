package dao.tables

import dao.data.{Item, ItemAndOrder, Order}
import service.DatabaseService
import slick.lifted.ProvenShape

import scala.concurrent.Future

trait ItemAndOrderTable extends OrderTable with ItemTable{

  protected val databaseService: DatabaseService
  import databaseService._
  import databaseService.drive.api._

  class ItemsAndOrders(tag: Tag) extends Table[ItemAndOrder](tag, "items-and-orders") {
    val id: Rep[Option[Int]] = column[Option[Int]]("id")
    val idItem: Rep[Int] = column[Int]("id-item")
    val idOrder: Rep[Int] = column[Int]("id-order")

    def * : ProvenShape[ItemAndOrder] = (id, idItem, idOrder) <> ((ItemAndOrder.apply _).tupled, ItemAndOrder.unapply)
    def itemFK = foreignKey("item", idItem, itemTable)(_.id.get)
    def orderFK = foreignKey("order", idOrder, orderTable)(_.id.get)
  }

  val itemAndOrderTable = TableQuery[ItemsAndOrders]

  def insert(itemAndOrder: ItemAndOrder): Future[ItemAndOrder] =
    db.run(itemAndOrderTable returning itemAndOrderTable += itemAndOrder)

  def getAllItemsAndOrders: Future[Seq[ItemAndOrder]] =
    db.run(itemAndOrderTable.result)

  def selectAllItemsAndOrders: Future[Seq[ItemAndOrder]] =
    db.run(itemAndOrderTable.result)

  def getAll: Future[Seq[(Item, Order)]] = {
    val query = for {
      io <- itemAndOrderTable
      i <- itemTable if i.id === io.idItem
      o <- orderTable if o.id === io.idOrder
    } yield(i, o)

    db.run(query.result)
  }
}
