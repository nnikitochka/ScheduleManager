package ru.nnedition.schedule

import ru.nnedition.schedule.module.ScheduleModule

class Building(
    val shortName: String,
    val fullName: String,
    val address: String,
) {
    fun format(module: ScheduleModule) = module.config.buildingsDataFormat
        .replace("{short_name}", shortName)
        .replace("{full_name}", fullName)
        .replace("{address}", address)
}