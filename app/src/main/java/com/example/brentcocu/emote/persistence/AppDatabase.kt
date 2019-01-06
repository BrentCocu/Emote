package com.example.brentcocu.emote.persistence

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.datamodels.Moment
import com.example.brentcocu.emote.util.Constants
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.util.*

@Database(
    entities = [Emotion::class, Moment::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(), AnkoLogger {

    abstract fun emotionDao(): EmotionDao
    abstract fun momentDao(): MomentDao

    private fun populate() {
        val emotions = listOf(
            Emotion(1,"Angry", Color.parseColor("#d50000")),
            Emotion(2,"Anxious", Color.parseColor("#43a047")),
            Emotion(3,"Happy", Color.parseColor("#1976d2"))
        )
        val moments = listOf(
            Moment(
                1,
                1,
                Date(),
                "I have main constraint layout, and also additional layout footer where i inflate it and add it to my main constraint. The problem is even though i set constraint to set the width to full (match_parent) its not working it center the view but it wrap content its not extended through all screen.",
                listOf("Jane Doe", "Harry Tubman"),
                "I have main constraint layout, and also additional layout footer where i inflate it and add it to my main constraint. The problem is even though i set constraint to set the width to full (match_parent)."
            ),
            Moment(
                2,
                3,
                Date(),
                "I feel happy",
                listOf("Brent Cocu", "Karel V", "Frederik D"),
                "Bit hungry"
            )
        )
        Schedulers.io().doAsync {
            this@AppDatabase.clearAllTables()
            this@AppDatabase.emotionDao()
                .insertAll(emotions)
                .doOnComplete { info("Database reinitialised - Emotions finished") }
                .doOnError { error("@ populate", it) }
                .subscribe()
            this@AppDatabase.momentDao()
                .insertAll(moments)
                .doOnComplete { info("Database reinitialised - Moments finished") }
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