import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(SpacesAroundMultiImports, false)

name := "spark-stringmetric"

spName := "MrPowers/spark-stringmetric"

spShortDescription := "stringmetric algorithms in spark"

spDescription := "Spark functions to run popular phonetic and string matching algorithms"

version := "0.2.0"

scalaVersion := "2.11.12"
sparkVersion := "2.4.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0" % "provided"

libraryDependencies += "org.apache.commons" % "commons-text" % "1.1" % "provided"

libraryDependencies += "mrpowers" % "spark-daria" % "0.27.0-s_2.11" % "test"
libraryDependencies += "MrPowers" % "spark-fast-tests" % "0.17.2-s_2.11" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}

// All Spark Packages need a license
licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

fork in Test := true

javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled","-Duser.timezone=GMT")