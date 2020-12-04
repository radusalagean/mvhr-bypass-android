package com.radusalagean.mvhrbypass.di

import com.radusalagean.mvhrbypass.activity.MainActivity
import com.radusalagean.mvhrbypass.generic.activity.ActivityContract
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import com.radusalagean.mvhrbypass.generic.scheduler.BaseSchedulerProvider
import com.radusalagean.mvhrbypass.generic.scheduler.SchedulerProvider
import com.radusalagean.mvhrbypass.infobar.InfoBarManager
import com.radusalagean.mvhrbypass.screen.connect.ConnectFragment
import com.radusalagean.mvhrbypass.screen.connect.ConnectModel
import com.radusalagean.mvhrbypass.screen.connect.ConnectMvp
import com.radusalagean.mvhrbypass.screen.connect.ConnectPresenter
import org.koin.dsl.module

val applicationModule = module {
    single<BaseSchedulerProvider> { SchedulerProvider() }
    scope<MainActivity> {
        scoped { InfoBarManager() }
    }
    scope<ConnectFragment> {
        scoped { (getSource() as BaseFragment).activity as ActivityContract }
        scoped<ConnectMvp.Model> { ConnectModel() }
        scoped<ConnectMvp.Presenter> { ConnectPresenter(get(), get(), get()) }
    }
}