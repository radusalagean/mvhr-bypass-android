package com.radusalagean.mvhrbypass.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class InitData(
    @SerializedName("state") val state: State,
    @SerializedName("temperatures") val temperatures: Temperatures
)