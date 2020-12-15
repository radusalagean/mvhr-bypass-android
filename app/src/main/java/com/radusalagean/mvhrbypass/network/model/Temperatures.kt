package com.radusalagean.mvhrbypass.network.model

import com.google.gson.annotations.SerializedName

data class Temperatures(
    @SerializedName("extEv") val extEv: Float,
    @SerializedName("extAd") val extAd: Float,
    @SerializedName("intAd") val intAd: Float,
    @SerializedName("intEv") val intEv: Float
)