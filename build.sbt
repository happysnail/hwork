name := "scala-learn2"

version := "1.0.0"

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-deprecation",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Ywarn-unused-import"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-actor"               % "2.5.1",
  "com.typesafe.akka"           %% "akka-http"                % "10.0.6",
  "de.heikoseeberger"           %% "akka-http-circe"          % "1.11.0",
  "com.typesafe.akka"           %% "akka-stream"              % "2.5.1",
  "com.typesafe.akka"           %% "akka-http-spray-json"     % "10.0.1",
  "com.typesafe.play"           %% "play-json"                % "2.4.0",
  "com.kifi"                    %% "json-annotation"          % "0.2",

  "com.typesafe.slick"          %% "slick"                    % "3.2.0-M2",
  "org.postgresql"              % "postgresql"                % "9.4-1201-jdbc41",
  "com.zaxxer"                  % "HikariCP"                  % "2.4.5",

  "io.circe"                    %% "circe-core"               % "0.6.1",
  "io.circe"                    %% "circe-generic"            % "0.6.1",
  "io.circe"                    %% "circe-parser"             % "0.6.1"
)

libraryDependencies ++= Seq(
  "org.flywaydb"                % "flyway-core"               % "4.1.2"
)

flywayUrl := "jdbc:postgresql://localhost/scala-learn"

flywayUser := "postgres"

flywayPassword := "password"