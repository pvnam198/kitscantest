package com.oc.kitscanner.features.detail_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.oc.domain.models.Document
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailEditViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    val document = savedStateHandle.get<Document>(DetailEditActivity.KEY_DOCUMENT)!!


}