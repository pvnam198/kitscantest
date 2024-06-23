package com.oc.data.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.oc.data.room_database.dao.DocumentDao
import com.oc.data.room_database.models.DocumentEntity

class Converters {
    @TypeConverter
    fun urlsToString(urls: List<String>): String {
        return Gson().toJson(urls)
    }

    @TypeConverter
    fun stringToUrls(string: String): List<String> {
        return Gson().fromJson(string, Array<String>::class.java).toList()
    }
}

@Database(entities = [DocumentEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "kit_scanner"
    }

    abstract fun documentDao(): DocumentDao
}