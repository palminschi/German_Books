package com.palmdev.german_books.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


fun String.decodeBase64Image(): Bitmap? {
    val bytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}