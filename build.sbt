val dottyVersion = "3.0.0-M2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "EconomicsResearch",
    version := "0.1.0",

    scalaVersion := dottyVersion,
    
    libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "5.0.0-alpha.2",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )
