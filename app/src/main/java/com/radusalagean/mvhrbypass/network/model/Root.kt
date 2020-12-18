package com.radusalagean.mvhrbypass.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Root<T>(
    @SerializedName("event") val event: String,
    @SerializedName("data") val data: T? = null
)