package com.radusalagean.mvhrbypass.network.event

import java.util.*

enum class SocketOutgoingEvent {

    REQUEST_HR_MODE_AUTO,
    REQUEST_HR_MODE_MANUAL,
    REQUEST_ENABLE_HR,
    REQUEST_DISABLE_HR,
    REQUEST_APPLY_STATE_TEMPERATURES;

    val eventName: String
        get() = name.toLowerCase(Locale.ROOT)
}