package ru.nnedition.configuration.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ConfigField(val section: String, val create: Boolean = true)