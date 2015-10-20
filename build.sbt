name := """spa"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
 "org.postgresql" % "postgresql" % "9.4-1203-jdbc42",
 "org.json" % "json" % "20140107",
 "com.github.fge" % "json-schema-validator" % "2.1.8",
 "org.hibernate" % "hibernate-entitymanager" % "5.0.2.Final",
  cache,
  javaWs
)