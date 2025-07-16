package ru.nnedition.schedule

import ru.nnedition.schedule.module.ScheduleModule

abstract class DataHandler(val module: ScheduleModule) {
    abstract fun updateBuildings()

    abstract fun updateGroups()

    abstract fun updateSchedule()

    abstract fun updateBellSchedule()
}