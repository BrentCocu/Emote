package com.example.brentcocu.emote.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brentcocu.emote.datamodels.Emotion

@Database(
    entities = [Emotion::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao
}