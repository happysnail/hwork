package dao.tables

import dao.data.Item
import service.DatabaseService
import slick.lifted.ProvenShape

import scala.concurrent.Future

trait ItemTable {

  protected val databaseService: DatabaseService
  import databaseService._
  import databaseService.drive.api._

  class ItemTable(tag: Tag) extends Table[Item](tag, "items") {
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    val name: Rep[String] = column[String]("name")
    val price: Rep[Int] = column[Int]("price")

    def * : ProvenShape[Item] = (id, name, price) <> ((Item.mapperTo _).tupled, Item.unapply)
  }

  val itemTable = TableQuery[ItemTable]

  def getByName(name: String): Query[ItemTable, Item, List] =
    itemTable.filter(_.name === name).to[List]

  def getById(id: Int): DBIO[Option[Item]] =
    itemTable.filter(_.id === id).result.headOption

  def delete(id: Int): Future[Int] =
    db.run{itemTable.filter(_.id === id).delete}

  def getFirstNItems(number: Int): DBIO[Seq[Item]] =
    itemTable.take(number).result

  def getNItems(number: Int, start: Int): DBIO[Seq[Item]] =
    itemTable.drop(start).take(number).result

  def deleteByName(name: String): Future[Int] =
    db.run(itemTable.filter(_.name === name).delete)

  def insert(item: Item): Future[Item] =
    db.run(itemTable returning itemTable += item)

  def getAllItems: Future[Seq[Item]] =
    db.run(itemTable.result)

  def findFirstNItems(number: Int): Future[Seq[Item]] =
    db.run(getFirstNItems(number))

  def findNItems(number: Int, start: Int): Future[Seq[Item]] =
    db.run(getNItems(number, start))

  def selectAll: Future[Seq[Item]] =
    db.run(itemTable.result)

  def findByName(name: String): Future[List[Item]] =
    db.run(getByName(name).result)

  def findById(id: Int): Future[Option[Item]] =
    db.run(getById(id))

}
