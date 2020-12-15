package com.radusalagean.mvhrbypass.network.model

import com.google.gson.annotations.SerializedName

data class InitData(
    @SerializedName("state") val state: State,
    @SerializedName("temperatures") val temperatures: Temperatures
)