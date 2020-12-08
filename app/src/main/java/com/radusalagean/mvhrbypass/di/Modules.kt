package com.radusalagean.mvhrbypass.di

import com.radusalagean.mvhrbypass.activity.MainActivity
import com.radusalagean.mvhrbypass.infobar.InfoBarManager
import com.radusalagean.mvhrbypass.screen.connect.ConnectFragment
import com.radusalagean.mvhrbypass.screen.main.MainFragment
import org.koin.dsl.module

val applicationModule = module {
    scope<MainActivity> {
        scoped { InfoBarManager() }
    }
    scope<ConnectFragment> {
    }
    scope<MainFragment> {
    }
}