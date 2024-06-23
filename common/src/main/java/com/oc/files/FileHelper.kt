package com.oc.files

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

suspend fun Context.saveFile(uri: Uri, output: File, onProgress: ((Int) -> Unit)? = null): Boolean =
    withContext(Dispatchers.IO) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(output)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            var totalBytesRead = 0f
            val totalSize: Int = inputStream!!.available()
            while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead
                withContext(Dispatchers.Main) { onProgress?.invoke((totalBytesRead / totalSize * 100f).toInt()) }
            }
            inputStream.close()
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
        return@withContext true
    }

