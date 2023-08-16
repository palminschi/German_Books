package com.palmdev.german_books.extensions

import android.content.Context
import android.util.TypedValue

fun Int.toSp(context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics)
}