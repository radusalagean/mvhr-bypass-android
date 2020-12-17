package com.radusalagean.mvhrbypass.network.event

import java.util.*

enum class SocketIncomingEvent {

    RESPONSE_INIT_DATA,
    RESPONSE_STATE,
    RESPONSE_TEMPERATURES,
    RESPONSE_CONNECTION_BUSY;

    val eventName: String
        get() = name.toLowerCase(Locale.ROOT)
}