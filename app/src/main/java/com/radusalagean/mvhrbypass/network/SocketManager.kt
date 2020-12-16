package com.radusalagean.mvhrbypass.network

import android.os.Handler
import com.google.gson.Gson
import com.radusalagean.mvhrbypass.network.event.SocketIncomingEvent
import com.radusalagean.mvhrbypass.network.event.SocketOutgoingEvent
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.Root
import com.radusalagean.mvhrbypass.network.model.State
import com.radusalagean.mvhrbypass.network.model.Temperatures
import okhttp3.*
import okio.ByteString
import timber.log.Timber

class SocketManager(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    private val mainHandler: Handler
) {

    private var subscribers: MutableSet<SocketSubscriber> = HashSet()

    private val socketListener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Timber.d("onOpen()")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Timber.d("onMessage(): text = $text")
            val root = gson.fromJson(text, Root::class.java)
            mainHandler.post {
                when (root.event) {
                    SocketIncomingEvent.RESPONSE_INIT_DATA.eventName ->
                        subscribers.forEach { it.onInitDataReceived(root.data as InitData) }
                    SocketIncomingEvent.RESPONSE_STATE.eventName ->
                        subscribers.forEach { it.onStateReceived(root.data as State) }
                    SocketIncomingEvent.RESPONSE_TEMPERATURES.eventName ->
                        subscribers.forEach { it.onTemperaturesReceived(root.data as Temperatures) }
                }
            }
            Timber.d(root.toString())
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Timber.d("onMessage(): bytes = $bytes")

        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Timber.w("onClosing(): code = $code | reason = $reason")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Timber.w("onClosed(): code = $code | reason = $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Timber.e("onFailure(): $t | $response")
            mainHandler.post { subscribers.forEach { it.onFailure() } }
        }
    }

    private lateinit var webSocket: WebSocket

    fun connect(address: String) {
        val request = Request.Builder()
            .url(address)
            .build()
        webSocket = okHttpClient.newWebSocket(request, socketListener)
    }

    fun requestHrModeAuto() {
        sendEventMessage<Unit>(SocketOutgoingEvent.REQUEST_HR_MODE_AUTO.eventName)
    }

    fun requestHrModeManual() {
        sendEventMessage<Unit>(SocketOutgoingEvent.REQUEST_HR_MODE_MANUAL.eventName)
    }

    fun requestEnableHr() {
        sendEventMessage<Unit>(SocketOutgoingEvent.REQUEST_ENABLE_HR.eventName)
    }

    fun requestDisableHr() {
        sendEventMessage<Unit>(SocketOutgoingEvent.REQUEST_DISABLE_HR.eventName)
    }

    fun requestApplyStateTemperatures(intEvMin: Int, extAdMin: Int, extAdMax: Int, hysteresis: Float) {
        sendEventMessage(
            eventName = SocketOutgoingEvent.REQUEST_APPLY_STATE_TEMPERATURES.eventName,
            data = State(
                intEvMin = intEvMin,
                extAdMin = extAdMin,
                extAdMax = extAdMax,
                hysteresis = hysteresis
            )
        )
    }

    private fun <T> sendEventMessage(eventName: String, data: T? = null) {
        val root = Root(eventName, data)
        val json = gson.toJson(root)
        webSocket.send(json)
    }

    fun subscribe(subscriber: SocketSubscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: SocketSubscriber) {
        subscribers.remove(subscriber)
    }
}