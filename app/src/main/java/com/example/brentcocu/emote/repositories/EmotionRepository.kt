package com.example.brentcocu.emote.repositories

import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.persistence.EmotionDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EmotionRepository {

    @Inject lateinit var dao: EmotionDao

    init {
        Application.appComponent.inject(this)
    }

    fun getAll(): Observable<List<Emotion>> {
        return dao.getAll().subscribeOn(Schedulers.io())
    }

    fun update(emotion: Emotion): Completable {
        return dao.update(emotion).subscribeOn(Schedulers.io())
    }

    fun insert(emotion: Emotion): Completable {
        return dao.insert(emotion).subscribeOn(Schedulers.io())
    }

    fun delete(emotion: Emotion): Completable {
        return dao.delete(emotion).subscribeOn(Schedulers.io())
    }
}