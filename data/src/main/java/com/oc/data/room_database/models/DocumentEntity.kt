package com.oc.data.room_database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oc.domain.models.Document

@Entity(tableName = "document")
class DocumentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "timeMillis")
    val timeMillis: Long,
    @ColumnInfo(name = "urls")
    val urls: List<String>
) {

    companion object {
        fun toEntity(document: Document): DocumentEntity {
            return DocumentEntity(
                document.id,
                document.name,
                document.timeMillis,
                document.urls
            )
        }
    }

    fun toDocument(): Document {
        return Document(id, name, timeMillis, urls)
    }

}