name := "scala-socket-listener"

Common.settings

lazy val root = (project in file("."))
  .aggregate(akkaIo)
  .aggregate(netty)
  .aggregate(fs2)
  .aggregate(javaSocket)

lazy val akkaIo = project in file("akka-io")
lazy val netty = project in file("netty")
lazy val fs2 = project in file("fs2")
lazy val javaSocket = project in file("java-socket")
