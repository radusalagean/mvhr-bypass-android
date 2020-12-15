package com.radusalagean.mvhrbypass.network.serialization

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.radusalagean.mvhrbypass.network.event.SocketIncomingEvent
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.Root
import java.lang.reflect.Type

class RootDeserializer : JsonDeserializer<Root<*>> {

    val gson = Gson();

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Root<*> {

        val jsonObject = json!!.asJsonObject
        val event = jsonObject[KEY_EVENT].asString
        when (event) {
            SocketIncomingEvent.RESPONSE_INIT_DATA.eventName -> {
                val initData: InitData = gson.fromJson(jsonObject[KEY_DATA], InitData::class.java)
                return Root(event, initData)
            }
        }
        error("Unknown event in deserializer")
    }

    companion object {
        const val KEY_EVENT = "event"
        const val KEY_DATA = "data"
    }
}