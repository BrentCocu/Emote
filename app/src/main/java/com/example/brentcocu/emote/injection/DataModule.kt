package com.example.brentcocu.emote.injection

import android.content.Context
import com.example.brentcocu.emote.persistence.AppDatabase
import com.example.brentcocu.emote.persistence.EmotionDao
import com.example.brentcocu.emote.persistence.MomentDao
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.repositories.MomentRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMomentRepository(): MomentRepository {
        return MomentRepository()
    }

    @Singleton
    @Provides
    fun provideEmotionRepository(): EmotionRepository {
        return EmotionRepository()
    }

    @Singleton
    @Provides
    fun provideMomentDao(database: AppDatabase): MomentDao {
        return database.momentDao()
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