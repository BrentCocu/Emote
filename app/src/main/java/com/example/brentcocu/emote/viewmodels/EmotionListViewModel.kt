package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.ui.EmotionListAdapterActions
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

class EmotionListViewModel : BaseViewModel(), EmotionListAdapterActions {

    @Inject
    lateinit var emotionRepository: EmotionRepository

    private val _emotionList = MutableLiveData<List<Emotion>>()
    val emotionList: LiveData<List<Emotion>> = _emotionList

    private val _selectedEmotion = MutableLiveData<Emotion>()
    val selectedEmotion: LiveData<Emotion> = _selectedEmotion

    init {
        Application.appComponent.inject(this)
        initEmotionList()
    }

    fun add(emotion: Emotion) {
        registerDisposable(
            emotionRepository.insert(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error("@ insert", it) }
                .subscribe()
        )
    }

    fun delete(emotion: Emotion) {
        registerDisposable(
            emotionRepository.delete(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error("@ remove", it) }
                .subscribe()
        )
    }

    fun update(emotion: Emotion) {
        registerDisposable(
            emotionRepository.update(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error("@ update", it) }
                .subscribe()
        )
    }

    override fun select(emotion: Emotion) {
        _selectedEmotion.value = emotion
        info("Selected ${emotion.name}")
    }

    private fun initEmotionList() {
        registerDisposable(
            emotionRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { _emotionList.value = it }
                .doOnError { error("@ getEmotionList", it) }
                .subscribe()
        )
    }
}