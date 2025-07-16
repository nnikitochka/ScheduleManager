package ru.nnedition.schedule

import ru.nnedition.schedule.module.ScheduleModule

class FinAltaiModule : ScheduleModule() {
    companion object {
        @JvmStatic
        lateinit var instance: FinAltaiModule
            private set
    }

    override val scheduleUpdateTime = 300L

    override fun onEnable() {
        instance = this

        this.schedule.dataHandler = DataManager(this).also {
            it.updateGroups()
            it.updateBuildings()
            it.updateBellSchedule()
        }
    }
}