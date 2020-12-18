package com.radusalagean.mvhrbypass.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class State(
    @SerializedName("hrModeAuto") val hrModeAuto: Boolean? = null,
    @SerializedName("hrDisabled") val hrDisabled: Boolean? = null,
    @SerializedName("intEvMin") val intEvMin: Int,
    @SerializedName("extAdMin") val extAdMin: Int,
    @SerializedName("extAdMax") val extAdMax: Int,
    @SerializedName("hysteresis") val hysteresis: Float
)