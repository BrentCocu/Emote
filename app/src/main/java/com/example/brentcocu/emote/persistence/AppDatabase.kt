package com.example.brentcocu.emote.persistence

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.util.Constants
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.*

@Database(
    entities = [Emotion::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(), AnkoLogger {
    abstract fun emotionDao(): EmotionDao

    private fun populate() {
        Schedulers.io().doAsync {
            this@AppDatabase.clearAllTables()
            this@AppDatabase.emotionDao()
                .insertAll(
                    listOf(
                        Emotion("Angry", Color.RED),
                        Emotion("Anxious", Color.GREEN),
                        Emotion("Happy", Color.BLUE)
                    )
                )
                .doOnComplete { info("Database reinitialised") }
                .doOnError { error("@ populate", it) }
                .subscribe()
        }
    }

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(applicationContext: Context) = instance ?: createInstance(applicationContext)

        private fun createInstance(applicationContext: Context): AppDatabase {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                Constants.DB_NAME
            ).build()
                .also {
                    it.populate()
                    instance = it
                    return it
                }
        }
    }
}