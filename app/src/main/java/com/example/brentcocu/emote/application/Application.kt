package com.example.brentcocu.emote.application

import android.app.Application
import com.example.brentcocu.emote.injection.AppComponent

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent.create(applicationContext)
    }

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }
}