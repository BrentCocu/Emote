package com.example.brentcocu.emote.persistence

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.util.Constants
import io.reactivex.schedulers.Schedulers

@Database(
    entities = [Emotion::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    private fun populate() {
        emotionDao().let {
            Schedulers.io().scheduleDirect {
                this.clearAllTables()
                it.insert(Emotion("Angry", Color.RED)).subscribe({ println("pop succes") }, { throwable -> println("pop fail ${throwable.message}") })
                it.insert(Emotion("Anxious", Color.GREEN)).subscribe({ println("pop succes") }, { throwable -> println("pop fail ${throwable.message}") })
                it.insert(Emotion("Happy", Color.BLUE)).subscribe({ println("pop succes") }, { throwable -> println("pop fail ${throwable.message}") })
            }
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