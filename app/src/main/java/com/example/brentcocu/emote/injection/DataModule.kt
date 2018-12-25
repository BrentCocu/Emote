package com.example.brentcocu.emote.injection

import android.content.Context
import com.example.brentcocu.emote.persistence.AppDatabase
import com.example.brentcocu.emote.persistence.EmotionDao
import com.example.brentcocu.emote.repositories.EmotionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideEmotionRepository(): EmotionRepository {
        return EmotionRepository()
    }

    @Singleton
    @Provides
    fun provideEmotionDao(database: AppDatabase): EmotionDao {
        return database.emotionDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase {
        return AppDatabase.getInstance(applicationContext)
    }
}