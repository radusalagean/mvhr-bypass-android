package com.radusalagean.mvhrbypass.application

import timber.log.Timber

class MvhrDebugApp : MvhrApp() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}