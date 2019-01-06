package com.example.brentcocu.emote.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.R
import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.datamodels.Emotion
import com.example.brentcocu.emote.datamodels.Moment
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.repositories.MomentRepository
import com.example.brentcocu.emote.ui.moments.MomentListAdapterActions
import com.example.brentcocu.emote.ui.moments.MomentOverviewFragmentActions
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.error
import java.util.*
import javax.inject.Inject

class MomentOverviewViewModel: BaseViewModel(), MomentListAdapterActions, MomentOverviewFragmentActions {

    @Inject
    lateinit var momentRepository: MomentRepository
    @Inject
    lateinit var emotionRepository: EmotionRepository

    private val emotions: MutableLiveData<List<Emotion>> = MutableLiveData()

    private var query = ""

    private var _momentUnfiltered: List<Moment> = listOf()
    private val _moments: MutableLiveData<List<Moment>> = MutableLiveData()
    val moments: LiveData<List<Moment>> = _moments

    init {
        Application.appComponent.inject(this)
        initEmotionList()
        initMomentList()
    }

    override fun getEmotionById(id: Int): Emotion? = emotions.value?.find { it.id == id }

    override fun filterByContent(query: String) {
        this.query = query.trim()
        updateMomentList()
    }

    override fun filterByEmotion(emotion: Emotion) {

     }

    override fun filterByDate(from: Date, to: Date) {

    }

    // TODO: fix unclickable view to clear textfield

    private fun initEmotionList() {
        registerDisposable(
            emotionRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { emotions.value = it }
                .doOnError {
                    error("@ getEmotionList", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
    }

    private fun initMomentList() {
        registerDisposable(
            momentRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    _momentUnfiltered = it
                    updateMomentList()
                }
                .doOnError {
                    error("@ getMomentList", it)
                    sendMessage(R.string.error_generic)
                }
                .subscribe()
        )
    }

    private fun updateMomentList() {
        val filteredList = if (query.isEmpty()) _momentUnfiltered else {
            _momentUnfiltered.filter {
                it.description.contains(query, true)
                    .or(it.sensations.contains(query, true))
                    .or(it.people.any { person -> person.contains(query, true) })
            }
        }
        _moments.value = filteredList.sortedByDescending { it.date }
    }

    companion object {
        fun getScopedInstance(activity: FragmentActivity): MomentOverviewViewModel = ViewModelProviders
            .of(activity)
            .get(MomentOverviewViewModel::class.java)
    }
}