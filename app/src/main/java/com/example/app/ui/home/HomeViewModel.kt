package com.example.app.ui.home

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {
    private var _seconds:MutableLiveData<Int> = MutableLiveData()
    private var running = false

    //var wasRunning = false
    val seconds: LiveData<Int> = _seconds


    init {
        runTimer()
    }
    fun start(){
        running = true
    }

    fun stop(){
        running=false
    }

    fun reset(){
        running = true
        _seconds.value = 0
    }

    fun runTimer() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                //val hours = seconds / 3600
                //val minutes = (seconds % 3600) / 60
                //val secs = seconds % 60
                //textView3.text = String.format("%02d:%02d:%02d",hours,minutes,secs)
                if (running) {
                    val sec = _seconds.value ?: 0
                    _seconds.value = sec + 1
                }

                handler.postDelayed(this,1000)
            }

        }
        handler.post(runnable)


    }
}