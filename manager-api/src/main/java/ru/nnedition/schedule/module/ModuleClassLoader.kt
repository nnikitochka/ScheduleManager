package ru.nnedition.schedule.module

import nn.edition.yalogger.logger
import java.io.File
import java.net.URLClassLoader
import java.util.jar.JarFile

class ModuleClassLoader : URLClassLoader {
    private val logger = logger(this.javaClass)

    val file: File
    val jar: JarFile
    var module: ScheduleModule? = null
        private set

    constructor(
        name: String,
        jar: JarFile,
        file: File,
        parent: ClassLoader
    ) : super(name, arrayOf(file.toURI().toURL()), parent) {
        this.file = file
        this.jar = jar
        val manifestAttributes = jar.manifest.mainAttributes!!

        val mainClass = manifestAttributes.getValue("Main-Class") ?: run {
            logger.error("Манифест $name не содержит информацию о главном классе!")
            return
        }

        val clazz = try {
            loadClass(mainClass)
        } catch (e: ClassNotFoundException) {
            logger.error("Главный класс $name по пути $mainClass не найден.", e)
            return
        }

        if (!ScheduleModule::class.java.isAssignableFrom(clazz)) {
            logger.error("Главный класс $name по пути $mainClass не является модулем бота расписания!")
            return
        }

        this.module = try {
            clazz.getDeclaredConstructor().newInstance() as ScheduleModule
        } catch (e: Exception) {
            logger.error("Не удалось создать экземпляр $mainClass: ${e.message}")
            return
        }
    }
}