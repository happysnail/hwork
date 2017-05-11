package config

import com.typesafe.config.Config

class ScalaLearnConfig(rawConfig: Config) {
  val name: String = rawConfig.getString("name")
}
