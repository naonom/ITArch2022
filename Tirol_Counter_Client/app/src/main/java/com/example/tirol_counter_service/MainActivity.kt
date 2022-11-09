package com.example.tirol_counter_service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.tirol_counter_client.R


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var m: EditText
    private lateinit var textMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        textMessage = findViewById(R.id.textView)
        m = findViewById(R.id.money)
    }

    override fun onStart() {
        super.onStart()
        if (iRemoteService == null){
            val it = Intent("MyRemoteService")
            it.setPackage("com.example.tirol_counter_service")
            bindService(it, mConnection, BIND_AUTO_CREATE)
            Log.d("intennt","correct" )
        }

        button.setOnClickListener {
            var money = m.text.toString().toInt()
            var result = iRemoteService?.tirol(money)
            Log.d("TAG", result.toString())
            textMessage.setText(result.toString())
        }
    }
    override fun onResume() {
        super.onResume()
    }

    var iRemoteService: IMyAidlInterface? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder) {
            iRemoteService = IMyAidlInterface.Stub.asInterface(service)
        }
        override fun onServiceDisconnected(className: ComponentName?) {
            Log.e("ClientApp", "Service has unexpectedly disconnected")
            iRemoteService = null
        }
    }
}
