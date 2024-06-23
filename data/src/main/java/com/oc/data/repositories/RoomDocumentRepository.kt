package com.oc.data.repositories

import com.oc.data.room_database.dao.DocumentDao
import com.oc.data.room_database.models.DocumentEntity
import com.oc.domain.models.Document
import com.oc.domain.repositories.DocumentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDocumentRepository(
    private val documentDao: DocumentDao
) : DocumentRepository {

    override suspend fun getAll(): List<Document> = withContext(Dispatchers.IO) {
        val entities: List<DocumentEntity> = documentDao.getAll()
        return@withContext entities.map { it.toDocument() }
    }

    override suspend fun insert(document: Document) = withContext(Dispatchers.IO) {
        documentDao.insert(DocumentEntity.toEntity(document))
    }

    override suspend fun insertAll(documents: List<Document>) = withContext(Dispatchers.IO) {
        documents.forEach { document -> insert(document) }
    }
}