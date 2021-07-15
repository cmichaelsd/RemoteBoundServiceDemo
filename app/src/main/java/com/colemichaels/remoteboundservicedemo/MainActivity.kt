package com.colemichaels.remoteboundservicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.colemichaels.remoteboundservicedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    var myService: Messenger? = null
    var isBound: Boolean = false

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(applicationContext, RemoteService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        binding.button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!isBound) return

        val msg = Message.obtain()

        val bundle = Bundle().apply {
            putString(DATA_KEY, "Message Received")
        }

        msg.data = bundle

        try {
            myService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val DATA_KEY = "data_key"
    }
}