package com.oc.domain.callbacks

import com.oc.domain.models.Document

interface OnScanListener {

    fun onSaving()

    fun onSavingProgress(progress: Int)

    fun onCancel()

    fun onFailure(cause: String?= null)

    fun onSuccess(document: Document)

}