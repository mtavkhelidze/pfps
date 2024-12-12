ThisBuild / version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "pfps",
    idePackagePrefix := Some("ge.zgharbi.pfps"),
    libraryDependencies ++= Seq(
      compilerPlugin(
        "org.typelevel" %% "kind-projector" % "0.13.3"
          cross CrossVersion.full,
      ),
      "co.fs2" %% "fs2-core" % "3.11.0",
      "dev.optics" %% "monocle-core" % "3.3.0",
      "dev.optics" %% "monocle-macro" % "3.3.0",
      "eu.timepit" %% "refined" % "0.11.2",
      "eu.timepit" %% "refined-cats" % "0.11.2",
      "io.estatico" %% "newtype" % "0.4.4",
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.typelevel" %% "cats-effect" % "3.5.7",
      "org.typelevel" %% "cats-mtl" % "1.5.0",
      "org.typelevel" %% "squants" % "1.8.3",
      "tf.tofu" %% "derevo-cats" % "0.13.0",
      "tf.tofu" %% "derevo-circe-magnolia" % "0.13.0",
    ),
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-Wconf:cat=unused:info",
      "-Xsource:3",
      "-Xsource:3-cross",
    ),
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / excludeLintKeys += idePackagePrefix
// Compile / run / fork := true
