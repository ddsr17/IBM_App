name := """IBM_App"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "3.0.0-alpha1",
  "org.webjars" % "jquery-ui" % "1.11.4",
  "org.webjars" % "bootstrap" % "3.1.1-2",
  "org.webjars.bower" % "angularjs" % "1.4.1",
  "org.webjars.bower" % "angular-route" % "1.4.1",
  "org.webjars" % "angular-strap" % "2.2.3",
  "org.webjars" % "angular-material" % "0.10.0",
  "org.webjars" % "ngDialog" % "0.3.12-1",
  "org.webjars" % "angular-local-storage" % "0.2.1"
)

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "0.11.7"
)


libraryDependencies += "org.mongodb" %% "casbah" % "2.8.2"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
