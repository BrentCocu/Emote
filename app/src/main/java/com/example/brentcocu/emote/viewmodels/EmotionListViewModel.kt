package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.repositories.EmotionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EmotionListViewModel : BaseViewModel(), EmotionListActions {

    @Inject
    lateinit var emotionRepository: EmotionRepository

    private val _data: MutableLiveData<List<Emotion>> = MutableLiveData()
    val data: LiveData<List<Emotion>> = _data

    init {
        Application.appComponent.inject(this)
        _data.value = listOf()
        getData()
    }

    override fun insert(emotion: Emotion) {
        registerDisposable(
            emotionRepository.insert(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { println(it.message) }
                .subscribe()
        )
    }

    override fun remove(emotion: Emotion) {
        registerDisposable(
            emotionRepository.delete(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { println(it.message) }
                .subscribe()
        )
    }

    private fun getData() {
        registerDisposable(
            emotionRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { _data.value = it }
                .doOnError { println(it.message) }
                .subscribe()
        )
    }
}

interface EmotionListActions {
    fun insert(emotion: Emotion)
    fun remove(emotion: Emotion)
}