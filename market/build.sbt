name := """market"""
organization := "cars"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,SbtWeb)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "com.h2database" % "h2" % "1.4.199"
libraryDependencies += "org.mybatis" % "mybatis" % "3.5.6"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.5"
libraryDependencies += "org.mockito" % "mockito-core" % "3.6.0" % "test"
libraryDependencies ++= Seq(javaJdbc)
libraryDependencies ++= Seq(evolutions, jdbc)

EclipseKeys.preTasks := Seq(compile in Compile)

// Java project. Don't expect Scala IDE
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java

// Use .class files instead of generated .scala files for views and routes
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)
