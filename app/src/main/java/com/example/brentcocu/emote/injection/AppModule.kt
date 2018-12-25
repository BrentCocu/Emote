package com.example.brentcocu.emote.injection

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.brentcocu.emote.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(applicationContext: Context): SharedPreferences {
        return applicationContext.getSharedPreferences(Constants.SP_GLOBAL_KEY, Activity.MODE_PRIVATE)
    }
}