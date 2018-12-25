package com.example.brentcocu.emote.injection

import android.content.Context
import com.example.brentcocu.emote.viewmodels.EmotionListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    // Register classes requiring injection
    fun inject(emotionListViewModel: EmotionListViewModel)

    companion object Factory {
        fun create(applicationContext: Context): AppComponent {
            return DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .dataModule(DataModule())
                .build()
        }
    }
}