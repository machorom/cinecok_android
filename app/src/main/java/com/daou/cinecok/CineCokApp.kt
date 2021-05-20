package com.daou.cinecok

import android.app.Application
import com.daou.cinecok.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CineCokApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CineCokApp)
            modules(listOf(repositoryModule,apiModule,localDBModule,resourceModule))
        }
    }
}