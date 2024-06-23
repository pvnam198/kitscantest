package com.oc.kitscanner.features.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oc.domain.callbacks.OnScanListener
import com.oc.domain.models.Document
import com.oc.domain.repositories.DocumentRepository
import com.oc.domain.services.ScannerService
import com.oc.kitscanner.features.main.models.DocumentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : ViewModel() {

    private val _documentResult = MutableLiveData<DocumentResult>()
    val documentResult: LiveData<DocumentResult> = _documentResult

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    private val _documents = MutableLiveData<List<Document>>()
    val documents: LiveData<List<Document>> = _documents

    fun scanAndSaveToDatabase(scannerService: ScannerService) {
        scannerService.scan(object : OnScanListener {
            override fun onSaving() {
                _documentResult.value = DocumentResult.Saving
            }

            override fun onSavingProgress(progress: Int) {
                _progress.value = progress
            }

            override fun onCancel() {
                _documentResult.value = DocumentResult.Cancel
            }

            override fun onSuccess(document: Document) {
                _documentResult.value = DocumentResult.Success(document)
                insertToCurrentList(document)
                insertToDatabase(document)
            }

            override fun onFailure(cause: String?) {
                _documentResult.value = DocumentResult.Failure(cause)
            }
        })
    }

    private fun insertToCurrentList(document: Document) {
        viewModelScope.launch {
            _documents.value = _documents.value?.toMutableList()?.apply { add(0, document) }
        }
    }

    private fun insertToDatabase(document: Document) {
        viewModelScope.launch { documentRepository.insert(document) }
    }

    fun getAllDocuments() {
        viewModelScope.launch { getDocuments() }
    }

    private suspend fun getDocuments() {
        _documents.value = documentRepository.getAll()
    }

}