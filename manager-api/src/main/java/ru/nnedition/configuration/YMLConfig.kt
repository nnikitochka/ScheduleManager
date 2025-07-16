package ru.nnedition.configuration

import ru.nnedition.schedule.utils.FileUtils
import ru.nnedition.configuration.annotations.ConfigFieldProcessor
import ru.nnedition.configuration.file.YamlConfiguration
import java.io.File
import java.lang.Exception

abstract class YMLConfig(
    val filePath: String,
    val resourcePath: String? = null,
) {

    lateinit var file: File
    lateinit var config: YamlConfiguration

    fun load(): YMLConfig {
        file = File(filePath)
        if (!file.exists()) {
            resourcePath?.let {
                FileUtils.saveResource(filePath, false)
            }
            file.createNewFile()
        }

        config = YamlConfiguration()

        try {
            config.load(file)
            config.options().parseComments(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        ConfigFieldProcessor.process(this)
        save()

        onLoad()

        return this
    }

    open fun onLoad() {}

    fun save() {
        try {
            config.save(file)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun set(path: String, value: Any) {
        config.set(path, value)
        save()
    }
}
