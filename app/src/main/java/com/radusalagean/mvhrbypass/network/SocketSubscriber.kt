package com.radusalagean.mvhrbypass.network

import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.State
import com.radusalagean.mvhrbypass.network.model.Temperatures

interface SocketSubscriber {
    fun onFailure() {}
    fun onInitDataReceived(initData: InitData) {}
    fun onStateReceived(state: State) {}
    fun onTemperaturesReceived(temperatures: Temperatures) {}
    fun onConnectionBusy() {}
}