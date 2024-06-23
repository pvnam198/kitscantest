package com.oc.kitscanner.di

import android.content.Context
import androidx.room.Room
import com.oc.data.repositories.RoomDocumentRepository
import com.oc.data.room_database.AppDatabase
import com.oc.data.room_database.dao.DocumentDao
import com.oc.domain.repositories.DocumentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideDocumentDao(
        @ApplicationContext context: Context
    ): DocumentDao {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()
        return db.documentDao()
    }

    @Provides
    fun provideDocumentRepository(
        documentDao: DocumentDao
    ): DocumentRepository {
        return RoomDocumentRepository(documentDao)
    }

}