package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brentcocu.emote.models.Emotion

class EmotionListViewModel : ViewModel() {

    internal val dataSet: MutableLiveData<List<Emotion>> = MutableLiveData()

    // TODO(replace with real db-connection)
    private val db: MutableList<Emotion> = mutableListOf(
        Emotion("Angry"),
        Emotion("Anxious"),
        Emotion("Happy")
    )

    init {
        update()
    }

    fun insert(emotion : Emotion) {
        db.add(emotion)
        update()
    }

    fun remove(emotion : Emotion) {
        db.remove(emotion)
        update()
    }

    private fun update() { dataSet.value = db }
}