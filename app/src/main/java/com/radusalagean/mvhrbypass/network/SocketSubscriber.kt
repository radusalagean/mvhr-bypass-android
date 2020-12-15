package com.radusalagean.mvhrbypass.network

import com.radusalagean.mvhrbypass.network.model.InitData

interface SocketSubscriber {
    fun onFailure() {}
    fun onInitDataReceived(initData: InitData) {}
}