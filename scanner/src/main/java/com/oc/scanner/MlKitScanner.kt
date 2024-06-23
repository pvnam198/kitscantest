package com.oc.scanner

import android.app.Activity.RESULT_OK
import android.net.Uri
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.oc.converters.f04
import com.oc.converters.millisToTime
import com.oc.coroutines.mainScope
import com.oc.data.internal_storages.InternalManager
import com.oc.domain.callbacks.OnScanListener
import com.oc.domain.models.Document
import com.oc.domain.services.ScannerService
import com.oc.files.saveFile
import com.oc.maths.calculatePercent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MlKitScanner(private val activity: AppCompatActivity) : ScannerService {

    companion object {
        private const val NO_DOCUMENTS_FOUND = "No documents found"
    }

    private var saveListDocumentJob: Job? = null
    private var onScanListener: OnScanListener? = null
    private var currentDocument: Document? = null

    private val scannerLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val gmsDocumentScanningResult =
                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                if (gmsDocumentScanningResult != null) {
                    saveListDocument(gmsDocumentScanningResult)
                }
            }
        }

    private fun saveListDocument(gmsDocumentScanningResult: GmsDocumentScanningResult) {
        onScanListener?.onSaving()
        saveListDocumentJob = mainScope.launch {
            val pages = gmsDocumentScanningResult.pages
            if (pages.isNullOrEmpty()) {
                onScanListener?.onFailure(NO_DOCUMENTS_FOUND)
                return@launch
            }
            val time = System.currentTimeMillis()
            val documentName = millisToTime(time, f04)
            val urls = ArrayList<String>()
            val dir = InternalManager.createDocumentDir(activity, documentName)
            val document = Document(0, documentName, time, urls)
            currentDocument = document
            pages.forEachIndexed { index, page ->
                withContext(Dispatchers.IO) {
                    saveDocumentFile(dir, page.imageUri, onProgress = {
                        val progress = calculatePercent(pages.size, index, it)
                        onScanListener?.onSavingProgress(progress)
                    })?.let { urls.add(it) }
                }
            }
            if (urls.isNotEmpty()) {
                onScanListener?.onSuccess(document)
            } else {
                onScanListener?.onFailure(NO_DOCUMENTS_FOUND)
            }
        }
    }

    private suspend fun saveDocumentFile(
        dir: File,
        uri: Uri,
        onProgress: ((Int) -> Unit)? = null
    ): String? {
        val context = activity.applicationContext
        val documentName = "${System.nanoTime()}".plus(".jpg")
        val documentFile = File(dir, documentName)
        if (context.saveFile(uri, documentFile, onProgress)) {
            return documentFile.absolutePath
        }
        return null
    }

    override fun scan(ls: OnScanListener) {
        currentDocument = null
        onScanListener = ls
        val options =
            GmsDocumentScannerOptions.Builder().setGalleryImportAllowed(false)
                .setResultFormats(RESULT_FORMAT_JPEG)
                .setScannerMode(SCANNER_MODE_FULL).build()
        val scanner = GmsDocumentScanning.getClient(options)
        scanner.getStartScanIntent(activity).addOnSuccessListener { intentSender ->
            scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
        }.addOnFailureListener {
            it.printStackTrace()
            onScanListener?.onFailure(it.cause.toString())
        }
    }

    override fun cancel() {
        saveListDocumentJob?.cancel()
        currentDocument?.let {

        }
    }

}