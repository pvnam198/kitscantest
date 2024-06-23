package com.oc.data.internal_storages

import android.content.Context
import java.io.File

object InternalManager {
    private const val DOCUMENTS_DIR = "documents"

    private fun getRootDocumentDir(context: Context): File {
        val dir = File(context.filesDir, DOCUMENTS_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun createDocumentDir(context: Context, name: String): File {
        val dir = File(getRootDocumentDir(context), name)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }

    fun createDocumentFile(context: Context, name: String): File {
        return File(getRootDocumentDir(context), name)
    }

}