package com.example.brentcocu.emote.persistence

import androidx.room.*
import com.example.brentcocu.emote.datamodels.Moment
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MomentDao {

    @Query("SELECT * FROM moment")
    fun getAll(): Observable<List<Moment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moment: Moment): Completable

    // Purely exist for seeding the db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(moment: List<Moment>): Completable

    @Delete
    fun delete(moment: Moment): Completable

    @Update
    fun update(moment: Moment): Completable
}