package com.radusalagean.mvhrbypass.application

import android.app.Application
import com.radusalagean.mvhrbypass.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MvhrApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MvhrApp)
            modules(applicationModule)
        }
    }
}