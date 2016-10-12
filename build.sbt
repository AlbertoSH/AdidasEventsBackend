name := "adidas_events"

version := "1.0"

lazy val `adidas_events` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "com.tngtech.jgiven" % "jgiven-html5-report" % "0.11.3" % "test",
  "com.tngtech.jgiven" % "jgiven-junit" % "0.11.3" % "test",
  "org.mockito" % "mockito-core" % "2.0.44-beta" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.3" % "test"
)

libraryDependencies ++= Seq(
  "io.reactivex" % "rxjava" % "1.2.1",
  "org.mongodb" % "mongodb-driver-rx" % "1.2.0",
  "com.github.albertosh" % "swagplash" % "1.5.1"
)