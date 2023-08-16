package com.palmdev.german_books.extensions

import android.net.Uri

fun String.toAssetsUri() : Uri {
    return Uri.parse("file:///android_asset/$this")
}