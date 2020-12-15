package com.radusalagean.mvhrbypass.network.model

import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("hrModeAuto") val hrModeAuto: Boolean,
    @SerializedName("hrDisabled") val hrDisabled: Boolean,
    @SerializedName("intEvMin") val intEvMin: Int,
    @SerializedName("extAdMin") val extAdMin: Int,
    @SerializedName("extAdMax") val extAdMax: Int,
    @SerializedName("hysteresis") val hysteresis: Float
)