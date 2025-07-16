plugins {
    id("com.gradleup.shadow") version "8.3.0"
    id("net.kyori.blossom") version "2.1.0"
}

group = "ru.nnedition.schedule"
version = "1.0"

dependencies {
    // Котлин
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":manager-api"))

    // Для тг бота
    implementation("org.telegram:telegrambots-longpolling:9.0.0")
    implementation("org.telegram:telegrambots-client:9.0.0")

    // Логи
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.github.nnikitochka:YetAnotherLogger:1.1")
    implementation("com.github.nnikitochka:TimeFormatter:1.1")

    implementation("com.google.code.gson:gson:2.11.0")
}

sourceSets {
    main {
        blossom {
            kotlinSources {
                property("version", version.toString())
            }
        }
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "ru.nnedition.schedule.ScheduleManager"
            attributes["Implementation-Title"] = "ScheduleManager"
            attributes["Implementation-Version"] = version
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        archiveFileName.set("ScheduleBot.jar")
    }
}