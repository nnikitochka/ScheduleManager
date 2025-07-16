package ru.nnedition.schedule.module

import ru.nnedition.schedule.ScheduleManager
import ru.nnedition.schedule.telegram.TelegramBotManager
import java.io.File
import java.util.jar.JarFile
import kotlin.text.isEmpty

class ModulesLoader {
    /**
     * Метод для загрузки модулей.
     */
    fun loadModules() {
        ScheduleManager.logger.info("Начало загрузки модулей...")

        val modulesPath = File("modules")

        if (!modulesPath.exists() || !modulesPath.isDirectory) {
            ScheduleManager.logger.info("Папки модулей не существует и будет создана автоматически...")
            modulesPath.mkdirs()
            ScheduleManager.logger.info("Успешное создание папки моделей! Остановка загрузки модулей...")
            return
        }

        val files = modulesPath.listFiles { it.extension == "jar" }
        if (files == null || files.isEmpty()) {
            ScheduleManager.logger.info("Папка модулей была найдена, но она пуста. Остановка загрузки модулей...")
            return
        }

        ScheduleManager.logger.info("Папка модулей была найдена, начинается загрузка...")

        files.forEach {
            loadModule(it)
        }

        ScheduleManager.logger.info("Загрузка модулей окончена. Всего было загружено: ${ScheduleManager.modules.size}")
    }

    private fun loadModule(file: File) {
        val jar = JarFile(file)
        val fileName = file.name

        val manifestAttributes = jar.manifest?.mainAttributes ?: run {
            ScheduleManager.logger.error("$fileName не содержит файла манифеста!")
            return
        }

        val name = manifestAttributes.getValue("Implementation-Title") ?: run {
            ScheduleManager.logger.error("Манифест $fileName не содержит информацию о тайтле!")
            return
        }

        val classLoader = ModuleClassLoader(name, jar, file, this.javaClass.classLoader)

        val module = classLoader.module ?: return

        if (ScheduleManager.modules.any { it.name == module.name }) {
            ScheduleManager.logger.error("Была пресечена попытка загрузить модуль ${module.name}. Модуль с таким названием уже существует!")
            return
        }

        val version = manifestAttributes.getValue("Implementation-Version") ?: run {
            ScheduleManager.logger.error("Манифест $fileName не содержит информацию о версии!")
            return
        }

        val dataFolder = File(file.parentFile, name)
        dataFolder.mkdirs()

        module.init(file.path, name, version, dataFolder)

        val token = module.getToken()

        if (token == null || token.isEmpty()) {
            ScheduleManager.logger.warn("Токен модуля не найден. Модуль не будет загружен.")
            return
        }

        try {
            module.enable(TelegramBotManager(token))

            ScheduleManager.modules.add(module)
            ScheduleManager.logger.info("Успешная загрузка модуля ${module.name}.")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun unloadModules() {
        ScheduleManager.modules.filter { it.isLoaded }.forEach { module ->

            val classLoader = module.javaClass.classLoader

            if (classLoader !is ModuleClassLoader) {
                ScheduleManager.logger.error("d")
                return@forEach
            }

            try {
                module.disable()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                classLoader.jar.close()
                classLoader.close()

                ScheduleManager.modules.remove(module)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}