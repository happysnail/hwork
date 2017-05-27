package service

import dao.data.Order
import dao.tables.OrderTable
import messaging.serialised.{RequestOrder, ResponseOrder}
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class OrderService (override val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends OrderTable {

  def getOrder: Future[Seq[Order]] = selectAllOrders

  def addOrder(reqOrder: RequestOrder): Future[ResponseOrder] = {
    insertOrder(Order(reqOrder)).map(order => ResponseOrder(order.id.get))
  }

  def getOrderById(id: Int): Future[Option[Order]] = findOrderById(id)
  def getNOrders(number: Int): Future[Seq[Order]] = findFirstN(number)
  def allOrder: Future[List[Order]] = allOrders

  val orderTablee = TableQuery[Orders]

}
