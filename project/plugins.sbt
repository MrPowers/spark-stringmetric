logLevel := Level.Warn

resolvers += Resolver.bintrayIvyRepo(
  "s22s",
  "sbt-plugins"
)

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.4")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.3")
addSbtPlugin("com.47deg"  % "sbt-microsites" % "1.2.1")
