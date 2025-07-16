package ru.nnedition.schedule

data class Day(
    var date: String = "",
    var dayOfWeek: String = "",
    var lessons: List<Lesson> = emptyList(),
)