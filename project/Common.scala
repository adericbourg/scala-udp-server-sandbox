import sbt.Keys._
import sbt.Setting

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "socket",
    scalaVersion := "2.12.8",
    version := "0.0.1-SNAPSHOT",
    scalacOptions += "-Ypartial-unification"
  )
}
