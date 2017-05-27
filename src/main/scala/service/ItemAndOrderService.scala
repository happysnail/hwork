package service

import dao.tables.ItemAndOrderTable
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext

class ItemAndOrderService (override val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends ItemAndOrderTable{

  val itemAndOrderTablee = TableQuery[ItemsAndOrders]

}
