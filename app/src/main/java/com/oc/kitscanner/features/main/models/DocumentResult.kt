package com.oc.kitscanner.features.main.models

import com.oc.domain.models.Document

sealed class DocumentResult {
    data object Saving : DocumentResult()
    data class Success(val document: Document) : DocumentResult()
    data class Failure(val e: String? = null) : DocumentResult()
    data object Cancel : DocumentResult()
}