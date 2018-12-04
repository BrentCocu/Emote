package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brentcocu.emote.models.Emotion

class EmotionListViewModel : ViewModel(), EmotionListActions {

    private val _dataSet: MutableLiveData<List<Emotion>> = MutableLiveData()
    val dataSet: LiveData<List<Emotion>> = _dataSet

    // TODO(replace with real db-connection)
    private val db: MutableList<Emotion> = mutableListOf(
        Emotion("Angry"),
        Emotion("Anxious"),
        Emotion("Happy")
    )

    init {
        update()
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