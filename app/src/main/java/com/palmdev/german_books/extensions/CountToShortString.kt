package com.palmdev.german_books.extensions

import java.text.DecimalFormat
import kotlin.math.abs

fun Long.toShortString(): String {
    val absValue = abs(this)
    val decimalFormat = DecimalFormat("#.#")
    return when {
        absValue >= 1_000_000_000 -> decimalFormat.format(this / 1_000_000_000.0) + "B"
        absValue >= 1_000_000 -> decimalFormat.format(this / 1_000_000.0) + "M"
        absValue >= 1_000 -> decimalFormat.format(this / 1_000.0) + "K"
        else -> this.toString()
    }.removeSuffix(".0")
}

fun Int.toShortString(): String {
    val absValue = abs(this)
    val decimalFormat = DecimalFormat("#.#")
    return when {
        absValue >= 1_000_000_000 -> decimalFormat.format(this / 1_000_000_000.0) + "B"
        absValue >= 1_000_000 -> decimalFormat.format(this / 1_000_000.0) + "M"
        absValue >= 1_000 -> decimalFormat.format(this / 1_000.0) + "K"
        else -> this.toString()
    }.removeSuffix(".0")
}
