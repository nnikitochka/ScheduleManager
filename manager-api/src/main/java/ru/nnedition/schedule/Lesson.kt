package ru.nnedition.schedule

data class Lesson(
    var inTurn: Int,
    var inTurnGlobal: Int,
    var discipline: String,
    var startTime: String,
    var endTime: String,
    var type: Type,
    var audience: String,
    var building: Building,
    var teacher: String,
    var combined: List<String>,
) {
    class Type(
        val name: String,
        val difficulty: Int,
    )
}