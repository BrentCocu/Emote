package com.example.brentcocu.emote.injection

import android.content.Context
import com.example.brentcocu.emote.repositories.EmotionRepository
import com.example.brentcocu.emote.repositories.MomentRepository
import com.example.brentcocu.emote.viewmodels.EmotionManagementViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    // Register classes requiring injection
    fun inject(emotionManagementViewModel: EmotionManagementViewModel)
    fun inject(emotionRepository: EmotionRepository)
    fun inject(momentRepository: MomentRepository)

    companion object Factory {
        fun create(applicationContext: Context): AppComponent {
            return DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .dataModule(DataModule())
                .build()
        }
    }
}