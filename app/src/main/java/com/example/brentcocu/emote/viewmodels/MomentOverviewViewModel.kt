package com.example.brentcocu.emote.viewmodels

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.brentcocu.emote.application.Application
import com.example.brentcocu.emote.repositories.MomentRepository
import javax.inject.Inject

class MomentOverviewViewModel: BaseViewModel() {

    @Inject
    lateinit var momentRepository: MomentRepository

    init {
        Application.appComponent.inject(this)
    }

    companion object {
        fun getScopedInstance(activity: FragmentActivity): MomentOverviewViewModel = ViewModelProviders
            .of(activity)
            .get(MomentOverviewViewModel::class.java)
    }
}