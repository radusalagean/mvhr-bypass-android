package com.radusalagean.mvhrbypass.network.serialization

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.radusalagean.mvhrbypass.network.event.SocketIncomingEvent
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.Root
import com.radusalagean.mvhrbypass.network.model.State
import com.radusalagean.mvhrbypass.network.model.Temperatures
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
            SocketIncomingEvent.RESPONSE_STATE.eventName -> {
                val state: State = gson.fromJson(jsonObject[KEY_DATA], State::class.java)
                return Root(event, state)
            }
            SocketIncomingEvent.RESPONSE_TEMPERATURES.eventName -> {
                val temperatures: Temperatures = gson.fromJson(jsonObject[KEY_DATA], Temperatures::class.java)
                return Root(event, temperatures)
            }
        }
        error("Unknown event in deserializer")
    }

    companion object {
        const val KEY_EVENT = "event"
        const val KEY_DATA = "data"
    }
}