package com.radusalagean.mvhrbypass.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import com.google.gson.GsonBuilder
import com.radusalagean.mvhrbypass.activity.MainActivity
import com.radusalagean.mvhrbypass.haptic.HapticFeedbackManager
import com.radusalagean.mvhrbypass.infobar.InfoBarManager
import com.radusalagean.mvhrbypass.network.SocketManager
import com.radusalagean.mvhrbypass.network.model.Root
import com.radusalagean.mvhrbypass.network.serialization.RootDeserializer
import com.radusalagean.mvhrbypass.persistence.sharedprefs.SharedPreferencesConstants
import com.radusalagean.mvhrbypass.persistence.sharedprefs.SharedPreferencesRepository
import com.radusalagean.mvhrbypass.screen.connect.ConnectFragment
import com.radusalagean.mvhrbypass.screen.main.MainFragment
import okhttp3.OkHttpClient
import org.koin.dsl.module

val applicationModule = module {
    single { OkHttpClient() }
    single { SocketManager(get(), get(), get()) }
    single { RootDeserializer() }
    single {
        GsonBuilder()
            .registerTypeAdapter(Root::class.java, get<RootDeserializer>())
            .create()
    }
    single {
        get<Context>().getSharedPreferences(SharedPreferencesConstants.FILE_NAME, Context.MODE_PRIVATE)
    }
    single { SharedPreferencesRepository(get()) }
    single { Handler(Looper.getMainLooper()) }
    single { get<Context>().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    single { HapticFeedbackManager(get()) }
    scope<MainActivity> {
        scoped { InfoBarManager() }
    }
    scope<ConnectFragment> {
    }
    scope<MainFragment> {
    }
}