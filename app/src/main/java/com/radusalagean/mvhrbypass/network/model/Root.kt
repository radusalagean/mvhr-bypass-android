package com.radusalagean.mvhrbypass.network.model

import com.google.gson.annotations.SerializedName

data class Root<T>(
    @SerializedName("event") val event: String,
    @SerializedName("data") val data: T? = null
)