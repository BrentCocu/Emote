package com.example.brentcocu.emote.persistence

import androidx.room.*
import com.example.brentcocu.emote.datamodels.Emotion
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface EmotionDao {

    @Query("SELECT * FROM emotion")
    fun getAll(): Observable<List<Emotion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(emotion: Emotion): Completable

    @Delete
    fun delete(emotion: Emotion): Completable

    @Update
    fun update(emotion: Emotion): Completable
}