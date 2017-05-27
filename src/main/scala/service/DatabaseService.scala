package service

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import config.ScalaLearnConfig

import scala.util.Try

class DatabaseService (dbUrl: String, dbUser: String, dbPassword: String) {

  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(dbUrl)
  hikariConfig.setUsername(dbUser)
  hikariConfig.setPassword(dbPassword)

  private val dataSource = new HikariDataSource(hikariConfig)
  val drive = slick.jdbc.PostgresProfile
  import drive.api._
  val db: drive.backend.DatabaseDef = Database.forDataSource(dataSource)

  db.createSession()
}

object DatabaseService {
  def apply(config: ScalaLearnConfig): Try[DatabaseService] = {
    Try(new DatabaseService(config.dbUrl, config.dbUser, config.dbPassword))
  }
}