package com.oc.kitscanner.features.exports

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.oc.domain.models.Document
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExportDialogViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    val document = savedStateHandle.get<Document>(ExportDialog.KEY_DOCUMENT)!!

}