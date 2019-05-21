import mill._, scalalib._, publish._
import mill.scalalib.api.Util.{scalaBinaryVersion => binaryVersion}
import coursier.MavenRepository
import $ivy.`com.nikvanderhoof::mill-spark:0.1.0`
import com.nikvanderhoof.mill._

val dariaBuildMatrix = for {
  scala <- Seq("2.11.8", "2.12.8")
  spark <- Seq("2.1.0", "2.2.0", "2.3.1", "2.4.2")
  if !(scala >= "2.12.0" && spark < "2.4.0")
} yield (scala, spark)

object stringmetric extends Cross[StringmetricModule](dariaBuildMatrix: _*)

class StringmetricModule(val crossScalaVersion: String, val crossSparkVersion: String)
  extends CrossScalaSparkModule with PublishModule {
  def publishVersion = s"0.31.0_spark${binaryVersion(crossSparkVersion)}"
  def artifactName = "spark-stringmetric"
  override def pomSettings = PomSettings(
    description = "Spark functions to run popular phonetic and string matching algorithms",
    organization = "com.github.mrpowers",
    url = "https://www.github.com/mrpowers/spark-stringmetric",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("mrpowers", "spark-stringmetric"),
    developers = Seq(
      Developer("mrpowers", "Matthew Powers", "https://www.github.com/mrpowers")
    )
  )
  def compileIvyDeps = Agg(
    ivy"org.apache.commons:commons-text:1.1",
    spark"sql"
  )
  def repositories = super.repositories ++
    Seq(MavenRepository("https://dl.bintray.com/spark-packages/maven"))
  object test extends Tests {
    val majorMinorVersion = crossScalaVersion.split("\\.").dropRight(1).mkString(".")
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.1",
      ivy"org.apache.commons:commons-text:1.1",
      ivy"MrPowers:spark-fast-tests:0.19.1-s_${binaryVersion(crossScalaVersion)}",
      ivy"mrpowers:spark-daria:0.27.0-s_${majorMinorVersion}",
      spark"sql"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

}