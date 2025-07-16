package ru.nnedition.placeholders

class Argument<C>(val value: C) {
    companion object {
        inline fun <reified T>findSingle(vararg args: Argument<out Any>): T? =
            args.singleOrNull { it.value is T }?.value as? T
    }
}

inline fun <reified T>Array<Argument<out Any>>.findSingle(): T? =
    this.singleOrNull { it.value is T }?.value as? T