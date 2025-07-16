package ru.nnedition.configuration.annotations

import ru.nnedition.configuration.YMLConfig

object ConfigFieldProcessor {
    fun process(config: YMLConfig) {
        for (field in config.javaClass.declaredFields) {
            val annotation = field.getAnnotation(ConfigField::class.java)
            if (annotation != null) {
                field.isAccessible = true

                val value = config.config.get(annotation.section) ?: run {
                    if (annotation.create) {
                        config.config.set(annotation.section, field.get(config))
                    }
                    continue
                }

                field.set(config, value)
            }
        }
    }
}