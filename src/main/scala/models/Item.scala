package models

final case class Item(name: String, id: Int)
final case class Order(items: List[Item])