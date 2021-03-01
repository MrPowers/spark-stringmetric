organization := "com.github.mrpowers"

// required settings for sbt-microsites
name := "spark-stringmetric"
description := "Popular phonetic and string matching algorithms implemented in Spark"
organizationName := "MrPowers"
organizationHomepage := Some(url("https://github.com/MrPowers"))

version := "0.4.0"
crossScalaVersions := Seq("2.12.12")
scalaVersion := "2.12.12"
val sparkVersion = "3.0.1"

libraryDependencies += "org.apache.commons" % "commons-text" % "1.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "com.github.mrpowers" %% "spark-fast-tests" % "1.0.0" % "test"
libraryDependencies += "com.github.mrpowers" %% "spark-daria" % "1.0.0" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  Artifact.artifactName(sv, module, artifact).replaceAll(s"-${module.revision}", s"-${sparkVersion}${module.revision}")
}

credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")

fork in Test := true

javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled", "-Duser.timezone=GMT")

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/MrPowers/spark-stringmetric"))
developers ++= List(
  Developer("MrPowers", "Matthew Powers", "@MrPowers", url("https://github.com/MrPowers"))
)
scmInfo := Some(ScmInfo(url("https://github.com/MrPowers/spark-stringmetric"), "git@github.com:MrPowers/spark-stringmetric.git"))

updateOptions := updateOptions.value.withLatestSnapshots(false)

publishMavenStyle := true

publishTo := sonatypePublishToBundle.value

Global/useGpgPinentry := true

// sbt-ghpages plugin: https://github.com/sbt/sbt-ghpages
enablePlugins(SiteScaladocPlugin)
enablePlugins(GhpagesPlugin)
git.remoteRepo := "git@github.com:MrPowers/spark-stringmetric.git"
