package com.example.brentcocu.emote.repositories

import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Moment
import com.example.brentcocu.emote.persistence.MomentDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MomentRepository {

    @Inject
    lateinit var dao: MomentDao

    init {
        Application.appComponent.inject(this)
    }

    fun getAll(): Observable<List<Moment>> {
        return dao.getAll().subscribeOn(Schedulers.io())
    }

    fun update(moment: Moment): Completable {
        return dao.update(moment).subscribeOn(Schedulers.io())
    }

    fun insert(moment: Moment): Completable {
        return dao.insert(moment).subscribeOn(Schedulers.io())
    }

    fun delete(moment: Moment): Completable {
        return dao.delete(moment).subscribeOn(Schedulers.io())
    }
}