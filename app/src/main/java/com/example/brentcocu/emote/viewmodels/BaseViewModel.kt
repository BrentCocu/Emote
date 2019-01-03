package com.example.brentcocu.emote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brentcocu.emote.util.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import org.jetbrains.anko.AnkoLogger

abstract class BaseViewModel : ViewModel(), AnkoLogger {

    private val compositeDisposable = CompositeDisposable()

    private val _onMessage = MutableLiveData<Event<Int>>()
    val onMessage: LiveData<Event<Int>> = _onMessage

    protected fun sendMessage(resId: Int) = _onMessage.postValue(Event(resId))

    protected fun registerDisposable(disposable: Disposable) = disposable.addTo(compositeDisposable)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}