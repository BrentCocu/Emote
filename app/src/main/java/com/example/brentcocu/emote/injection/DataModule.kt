package com.example.brentcocu.emote.injection

import android.content.Context
import androidx.room.Room
import com.example.brentcocu.emote.persistence.AppDatabase
import com.example.brentcocu.emote.persistence.EmotionDao
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideEmotionRepository(emotionDao: EmotionDao): EmotionRepository {
        return EmotionRepository(emotionDao)
    }

    @Singleton
    @Provides
    fun provideEmotionDao(database: AppDatabase): EmotionDao {
        return database.emotionDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }
}