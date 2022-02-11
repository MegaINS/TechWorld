


val projectVersion = "0.1"

val projectScalaVersion = "2.13.8"

val lwjglVersion = "3.3.0"

name := "TechWorld"

version := projectVersion

lazy val common = project
        .settings(
            name := "Common",
            version := projectVersion,
            Compile / scalaSource := baseDirectory.value / "src",
            Compile / resourceDirectory := baseDirectory.value / "resources",
            scalaVersion := projectScalaVersion,
            libraryDependencies ++= Seq(
                "org.joml" % "joml" % "1.9.0",
                "org.slf4j" % "slf4j-api" % "1.7.25",
                "ch.qos.logback" % "logback-classic" % "1.2.3",
                "org.scala-lang" % "scala-reflect" % "2.13.1",
                "io.netty" % "netty-all" % "4.1.64.Final",
                "com.google.guava" % "guava" % "31.0.1-jre"

            )
        )

lazy val client = project
        .settings(
            name := "Client",
            version := projectVersion,
            Compile / scalaSource := baseDirectory.value / "src",
            Compile / resourceDirectory := baseDirectory.value / "resources",
            scalaVersion := projectScalaVersion,
            libraryDependencies ++= Seq(
                "org.lwjgl" % "lwjgl" % lwjglVersion,
                "org.lwjgl" % "lwjgl-glfw" % lwjglVersion,
                "org.lwjgl" % "lwjgl-opengl" % lwjglVersion,
                "org.lwjgl" % "lwjgl-stb" % lwjglVersion,


                "org.lwjgl" % "lwjgl" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-glfw" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-opengl" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",
                "org.lwjgl" % "lwjgl-stb" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos"
               // "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"
            )
        ).aggregate(common)
        .dependsOn(common)

lazy val server = project
        .settings(
            name := "Server",
            version := projectVersion,
            Compile / scalaSource := baseDirectory.value / "src",
            Compile / resourceDirectory := baseDirectory.value / "resources",
            scalaVersion := projectScalaVersion,
            libraryDependencies ++= Seq(
                //        "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.0"
            )
        ).aggregate(common)
        .dependsOn(common)