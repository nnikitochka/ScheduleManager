package ru.nnedition.placeholders

abstract class Placeholder(
    var content: String,
    val process: (text: String, args: Array<Argument<out Any>>) -> String
)