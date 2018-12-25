package com.example.brentcocu.emote.viewmodels

import android.graphics.Color
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

    private val _dataSet: MutableLiveData<List<Emotion>> = MutableLiveData()
    val dataSet: LiveData<List<Emotion>> = _dataSet

    // TODO(replace with real db-connection)
    private val db: MutableList<Emotion> = mutableListOf(
        Emotion("Angry", Color.RED),
        Emotion("Anxious", Color.GREEN),
        Emotion("Happy", Color.BLUE)
    )

    init {
        Application.appComponent.inject(this)
        update()
        println("\n\nStart")
        val sub = emotionRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println("\n\nInside")
                it.forEach { x -> println(x.name) }
            }
        registerDisposable(sub)
        println("\n\nEnd")
    }

    override fun insert(emotion: Emotion) {
        db.add(emotion)
        update()
    }

    override fun remove(emotion: Emotion) {
        db.remove(emotion)
        update()
    }

    private fun update() {
        _dataSet.value = db
    }

}

interface EmotionListActions {
    fun insert(emotion: Emotion)
    fun remove(emotion: Emotion)
}