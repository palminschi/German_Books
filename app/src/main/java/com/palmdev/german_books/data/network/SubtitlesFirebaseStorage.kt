package com.palmdev.german_books.data.network

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import com.palmdev.german_books.data.model.SubtitleDto
import com.palmdev.german_books.data.network.parser.SubtitlesParser
import com.palmdev.german_books.utils.CAUGHT_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubtitlesFirebaseStorage @Inject constructor() {

    private val mStorage = Firebase.storage
    private val mStorageRef = mStorage.reference
    private val maxDownloadSize = 1L * 1024 * 1024 // 1MB

    suspend fun getSubtitles(videoId: String, languageCode: String): List<SubtitleDto> {
        return try {
            val path = "subtitles/${languageCode.lowercase()}/${videoId}.srt"
            val fileRef = mStorageRef.child(path)
            val bytes: ByteArray = withContext(Dispatchers.IO) {
                try {
                    fileRef.getBytes(maxDownloadSize).await()
                } catch (e: StorageException) {
                    if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                        Log.w(CAUGHT_ERROR, "The file does not exist.")
                        null
                    } else {
                        throw e
                    }
                }
            } ?: return emptyList()
            bytes.let { SubtitlesParser.parseSrtByteArray(it) }
        } catch (e: Exception) {
            e.message?.let { Log.w(CAUGHT_ERROR, it) }
            emptyList()
        }
    }



}