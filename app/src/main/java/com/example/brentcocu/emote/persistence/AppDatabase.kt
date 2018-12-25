package com.example.brentcocu.emote.persistence

import android.graphics.Color
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brentcocu.emote.datamodels.Emotion

@Database(
    entities = [Emotion::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    init {
        this.emotionDao().let {
            it.insert(Emotion("Angry", Color.RED))
            it.insert(Emotion("Anxious", Color.GREEN))
            it.insert(Emotion("Happy", Color.BLUE))
            println("\n\n Test\n")
        }
    }
}