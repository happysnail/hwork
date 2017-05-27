package config

import com.typesafe.config.ConfigFactory

class ScalaLearnConfig() {
  private val rawConfig = ConfigFactory.load()
  private val httpConfig = rawConfig.getConfig("http")
  private val dbConfig = rawConfig.getConfig("database")

  val httpHost: String = httpConfig.getString("interface")
  val httpPort: Int = httpConfig.getInt("port")

  val dbUrl: String = dbConfig.getString("url")
  val dbUser: String = dbConfig.getString("user")
  val dbPassword: String = dbConfig.getString("password")
}
