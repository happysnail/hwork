name := "scala-learn2"

version := "1.0.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.1",
  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "com.typesafe.akka" %% "akka-stream" % "2.5.1",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1",
  "com.typesafe.play" %% "play-json" % "2.4.0",
  "com.kifi" %% "json-annotation" % "0.2"
)
    