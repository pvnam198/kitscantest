package com.oc.data.room_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oc.data.room_database.models.DocumentEntity

@Dao
interface DocumentDao {
    @Query("SELECT * FROM document ORDER BY timeMillis DESC")
    fun getAll(): List<DocumentEntity>

    @Insert
    fun insert(document: DocumentEntity)
}