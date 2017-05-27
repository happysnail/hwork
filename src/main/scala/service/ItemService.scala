package service

import dao.data.Item
import dao.tables.ItemTable
import messaging.serialised.{RequestItem, ResponseItem}

import scala.concurrent.{ExecutionContext, Future}

class ItemService (override val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends ItemTable {

  def addItem(reqItem: RequestItem): Future[ResponseItem] = {
    insert(Item(reqItem)).map(item => ResponseItem(item.id.get))
  }

  def getItems: Future[Seq[Item]] = selectAll

}
