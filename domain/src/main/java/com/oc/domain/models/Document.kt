package com.oc.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    val id: Int,
    val name: String,
    val timeMillis: Long,
    val urls: List<String>
): Parcelable