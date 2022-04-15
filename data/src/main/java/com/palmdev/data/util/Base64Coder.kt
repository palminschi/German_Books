package com.palmdev.data.util

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object Base64Coder {

    fun decodeImageToByte(encodedImage: String): Bitmap {
        val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }

    fun encodeImageFromAssets(context:Context, filePath: String): String {
        var encodedImageBase64: String
        val assetManager: AssetManager = context.assets
        var fileStream: InputStream? = null
        try {
            fileStream = assetManager.open(filePath)
            val bitmap = BitmapFactory.decodeStream(fileStream)

            val byteArrayOutputStream = ByteArrayOutputStream()

            val fileExtensionPosition = filePath.lastIndexOf('.')
            val fileExtension = filePath.substring(fileExtensionPosition + 1)
            if (fileExtension.equals("png", ignoreCase = true)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            } else if (fileExtension.equals(
                    "jpg",
                    ignoreCase = true
                ) || fileExtension.equals("jpeg", ignoreCase = true)
            ) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            }
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val imageBase64: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            encodedImageBase64 = imageBase64
        } catch (e: IOException) {
            e.printStackTrace()
            return "".also { encodedImageBase64 = it }
        } finally {
            try {
                fileStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return encodedImageBase64
    }
}