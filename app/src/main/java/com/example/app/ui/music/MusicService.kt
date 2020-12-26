package com.example.app.ui.music

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.example.app.MainActivity
import com.example.app.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MusicService : Service() {
    companion object {
        val Commond = "Operate"
    }
    val TAG = "MainActivity"
    val mediaPlayer = MediaPlayer()
    val musicList = mutableListOf<String>()
    val musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false
    val binder = MusicBinder()
    //val Commond = "Operate"

    inner class MusicBinder: Binder(){
        val musicName
                    get() = musicNameList.get(current)
        var currentPosition
                    get() = mediaPlayer.currentPosition
                    set(value) = mediaPlayer.seekTo(value)
        val duration
                    get() = mediaPlayer.duration
        val size
                    get() = musicList.size
        val currentIndex
                    get() = current
    }

    val Channel_ID = "my channel"
    val Notification_ID = 1

    override fun onCreate() {
        super.onCreate()
        getMusicList()

        mediaPlayer.setOnPreparedListener{
            it.start()

           // val notification2 = builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder: Notification.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(Channel_ID,"test",
                    NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(notificationChannel)
                builder = Notification.Builder(this,Channel_ID)
            }else {
                builder = Notification.Builder(this)
            }

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,1,intent, PendingIntent.FLAG_UPDATE_CURRENT)


            val notification2 = builder.setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("自制音乐播放器")
                .setContentText("正在播放-${musicNameList[current]}")
                .setContentIntent(pendingIntent)//点击通知跳转应用
                .setAutoCancel(true)//点击后自动清空
                .build()

            notificationManager.notify(Notification_ID,notification2)

        }

        //播放至列表末尾循环播放
        mediaPlayer.setOnCompletionListener {
            current++
            if(current >= musicList.size){
                current = 0
            }
            play()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val operate = intent?.getIntExtra(Commond,1)?:1
        when(operate){
            1 -> play()
            2 -> pause()
            3 -> stop()
            4 -> next()
            5 -> last()
        }
        return super.onStartCommand(intent, flags, startId)
    }

/*
    fun onPlay(v: View) {
        play()
    }*/
    fun pause() {
        if(isPause){
            isPause = false
        }else{
            mediaPlayer.pause()
            isPause = true
        }
    }
    fun stop() {
        mediaPlayer.stop()
        stopSelf()
    }
    fun next() {
        current++
        if(current >= musicList.size){
            current = 0
        }
        play()
    }
    fun last() {
        current--
        if(current < 0){
            current = musicList.size - 1
        }
        play()
    }

    fun play() {
        if (musicList.size == 0) return
        val path = musicList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
           // musicName.text = musicNameList[current]
            //count.text = "${current+1}/${musicList.size}"
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //放送通知
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(Channel_ID,"test",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this,Channel_ID)
        }else {
            builder = Notification.Builder(this)
        }

        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,1,intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val notification = builder.setSmallIcon(R.drawable.ic_notify)
            .setContentTitle("音乐")
            .setContentText("正在播放-${musicNameList[current]}")
            .setContentIntent(pendingIntent)//点击通知跳转应用
            .setAutoCancel(true)//点击后自动清空
            .build()

        notificationManager.notify(Notification_ID,notification)
        val intent2 = Intent(MyReceiverAction)
        intent2.putExtra("operate",1)
        intent2.setPackage(packageName)
        sendBroadcast(intent2)

    }
    override fun onBind(intent: Intent): IBinder {
        return binder
        //TODO("Return the communication channel to the service.")
    }

    private fun getMusicList(){
        val cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null,null)
        if (cursor != null){
            while (cursor.moveToNext()){
                val musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                val musicName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(TAG,"getMusicList: $musicPath name:$musicName")
            }
            cursor.close()
        }
    }
}
