plugins {
    id("com.gradleup.shadow") version "8.3.0"
}

group = "ru.nnedition.schedule"
version = "1.0"
val moduleName = "FinashkaAltai"

dependencies {
    compileOnly(project(":manager-api"))

    compileOnly("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jsoup:jsoup:1.20.1")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "ru.nnedition.schedule.FinAltaiModule"
            attributes["Implementation-Title"] = moduleName
            attributes["Implementation-Version"] = version
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        archiveFileName.set("$moduleName.jar")
        destinationDirectory = file("${rootProject.projectDir}/modules/")
    }
}