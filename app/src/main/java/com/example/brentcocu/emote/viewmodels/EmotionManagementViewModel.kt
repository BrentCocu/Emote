package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.ui.emotions.EmotionEditFragmentActions
import com.example.brentcocu.emote.ui.emotions.EmotionListAdapterActions
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.error
import javax.inject.Inject

class EmotionManagementViewModel : BaseViewModel(), EmotionListAdapterActions,
    EmotionEditFragmentActions {

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

    override fun add(emotion: Emotion): Boolean {
        if (!isValid(emotion)) return false
        registerDisposable(
            emotionRepository.insert(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { sendMessage(R.string.info_emotion_add) }
                .doOnError {
                    error("@ insert", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
        return true
    }

    override fun delete(emotion: Emotion) {
        registerDisposable(
            emotionRepository.delete(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { sendMessage(R.string.info_emotion_delete) }
                .doOnError {
                    error("@ remove", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
    }

    override fun update(emotion: Emotion): Boolean {
        if (!isValid(emotion)) return false
        registerDisposable(
            emotionRepository.update(emotion)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { sendMessage(R.string.info_emotion_save) }
                .doOnError {
                    error("@ update", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
        return true
    }

    override fun select(emotion: Emotion) {
        _selectedEmotion.value = emotion.copy()
    }

    private fun initEmotionList() {
        registerDisposable(
            emotionRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { _emotionList.value = it }
                .doOnError {
                    error("@ getEmotionList", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
    }

    private fun isValid(emotion: Emotion): Boolean {
        if (prepare(emotion).name.isEmpty()) {
            sendMessage(R.string.error_emotion_invalid_name)
            return false
        }
        return true
    }

    private fun prepare(emotion: Emotion): Emotion {
        return emotion.apply {
            name = name.trim()
        }
    }
}