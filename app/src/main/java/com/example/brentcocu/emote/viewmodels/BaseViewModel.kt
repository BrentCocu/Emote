package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val _onMessage = MutableLiveData<Int>()
    val onMessage: LiveData<Int> = _onMessage

    init {
        TODO("Define an inject")
    }

    protected fun sendMessage(resId: Int) = _onMessage.postValue(resId)
}