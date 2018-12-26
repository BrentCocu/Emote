package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.AnkoLogger

abstract class BaseViewModel : ViewModel(), AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

    private val _onMessage = MutableLiveData<Int>()
    val onMessage: LiveData<Int> = _onMessage

    protected fun sendMessage(resId: Int) = _onMessage.postValue(resId)

    protected fun registerDisposable(disposable: Disposable) = disposable.addTo(compositeDisposable)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}