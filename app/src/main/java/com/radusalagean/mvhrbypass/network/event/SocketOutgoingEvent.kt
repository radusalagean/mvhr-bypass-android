package com.radusalagean.mvhrbypass.network.event

import java.util.*

enum class SocketOutgoingEvent {

    REQUEST_STATE,
    REQUEST_TEMPERATURES;

    val eventName: String
        get() = name.toLowerCase(Locale.ROOT)
}