package com.colemichaels.remoteboundservicedemo

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast

class RemoteService : Service() {
    // A reference of a Messenger object containing an Handler implementation.
    private val myMessenger = Messenger(IncomingHandler())

    override fun onBind(intent: Intent): IBinder {
        // Return IBinder object from Messenger object
        return myMessenger.binder
    }

    inner class IncomingHandler : Handler(Looper.getMainLooper()) {
        // This method is called when a message is received from a client.
        // The Message object contains any data the client needs to send to the service.
        override fun handleMessage(msg: Message) {
            val data = msg.data
            val dataString = data.getString(DATA_KEY)
            Toast.makeText(applicationContext, dataString, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val DATA_KEY = "data_key"
    }
}