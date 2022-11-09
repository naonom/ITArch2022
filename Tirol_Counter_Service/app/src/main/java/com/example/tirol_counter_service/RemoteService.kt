package com.example.tirol_counter_service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RemoteService : Service() {

    private val binder = object : IMyAidlInterface.Stub() {
        override fun tirol(num: Int): Int {
            var count = num/24
            return count.toInt()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

}