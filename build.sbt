ThisBuild / version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "pfps",
    idePackagePrefix := Some("ge.zgharbi.pfps"),
    libraryDependencies ++= Seq(
      compilerPlugin(
        "org.scalameta" % "semanticdb-scalac" % "4.12.3" cross CrossVersion.full,
      ),
      compilerPlugin(
        "org.typelevel" %% "kind-projector" % "0.13.3" cross CrossVersion.full,
      ),
      "co.fs2" %% "fs2-core" % "3.11.0",
      "com.github.cb372" %% "cats-retry" % "3.1.3",
      "dev.optics" %% "monocle-core" % "3.3.0",
      "dev.optics" %% "monocle-macro" % "3.3.0",
      "dev.profunktor" %% "http4s-jwt-auth" % "2.0.2",
      "eu.timepit" %% "refined" % "0.11.3",
      "eu.timepit" %% "refined-cats" % "0.11.3",
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10",
      "io.circe" %% "circe-refined" % "0.15.1",
      "io.estatico" %% "newtype" % "0.4.4",
      "org.http4s" %% "http4s-circe" % "0.23.30",
      "org.http4s" %% "http4s-dsl" % "0.23.30",
      "org.http4s" %% "http4s-ember-client" % "0.23.30",
      "org.http4s" %% "http4s-ember-server" % "0.23.30",
      "org.tpolecat" %% "skunk-circe" % "0.6.4",
      "org.tpolecat" %% "skunk-core" % "0.6.4",
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.typelevel" %% "cats-effect" % "3.5.7",
      "org.typelevel" %% "cats-kernel" % "2.12.0",
      "org.typelevel" %% "cats-mtl" % "1.5.0",
      "org.typelevel" %% "log4cats-core" % "2.7.0",
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "org.typelevel" %% "twiddles-core" % "0.9.0",
      "org.typelevel" %% "squants" % "1.8.3",
      "tf.tofu" %% "derevo-cats" % "0.13.0",
      "tf.tofu" %% "derevo-circe-magnolia" % "0.13.0",
    ),
    libraryDependencySchemes ++= Seq(
      "io.circe" %% "circe-core" % VersionScheme.Always,
      "org.typelevel" %% "twiddles-core" % VersionScheme.Always,
    ),
    scalacOptions ++= List(
      "-Ymacro-annotations",
      "-Yrangepos",
      "-Wconf:cat=unused:info",
    ),
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / excludeLintKeys += idePackagePrefix
// Compile / run / fork := true
