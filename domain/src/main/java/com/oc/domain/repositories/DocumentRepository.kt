package com.oc.domain.repositories

import com.oc.domain.models.Document

interface DocumentRepository {

    suspend fun getAll(): List<Document>

    suspend fun insert(document: Document)

    suspend fun insertAll(documents: List<Document>)

}