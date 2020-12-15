package com.radusalagean.mvhrbypass.network

import android.os.Handler
import com.google.gson.Gson
import com.radusalagean.mvhrbypass.network.event.SocketIncomingEvent
import com.radusalagean.mvhrbypass.network.model.InitData
import com.radusalagean.mvhrbypass.network.model.Root
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

    fun subscribe(subscriber: SocketSubscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: SocketSubscriber) {
        subscribers.remove(subscriber)
    }
}