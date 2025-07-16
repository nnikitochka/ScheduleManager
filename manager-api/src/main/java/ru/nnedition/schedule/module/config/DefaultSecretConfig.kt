package ru.nnedition.schedule.module.config

import ru.nnedition.configuration.YMLConfig
import ru.nnedition.configuration.annotations.ConfigField
import ru.nnedition.schedule.module.ScheduleModule

/**
 * Абстрактный класс для конфигурации секретных данных модуля.
 */
open class DefaultSecretConfig(module: ScheduleModule) : YMLConfig("${module.dataFolder}/secret.yml") {
    @ConfigField("token", create = true)
    val botToken: String? = ""
}