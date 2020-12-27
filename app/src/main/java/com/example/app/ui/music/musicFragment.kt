package com.example.app.ui.music

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.app.R
import kotlinx.android.synthetic.main.music_fragment.*
import java.io.IOException
import kotlin.concurrent.thread


//const val MyReceiverAction = "com.example.app.ui.music.MusicService"
class musicFragment : Fragment() {

    companion object {
        fun newInstance() = musicFragment()
    }
    private lateinit var viewModel: MusicViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_fragment, container, false)
    }

    val TAG = "MainActivity"
    val mediaPlayer = MediaPlayer()
    var musicList = mutableListOf<String>()
    var musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false
    val Channel_ID = "my channel"
    val Notification_ID = 1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)
        viewModel.musicList.observe(requireActivity(),{
            musicList = it as MutableList<String>
        })
        viewModel.musicNameList.observe(requireActivity(),{
            musicNameList = it as MutableList<String>
        })

        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        //播放至列表末尾循环播放
        mediaPlayer.setOnCompletionListener {
            current++
            if (musicList != null) {
                if(current >= musicList.size){
                    current = 0
                }
            }
            play()
        }
        if (this.context?.let { ContextCompat.checkSelfPermission(it,android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
            this.activity?.let { ActivityCompat.requestPermissions(it,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0) }
        }else{
            viewModel.getMusicList()
        }




        //设置拖动条进度与歌曲播放同步
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekbar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        //每秒更新进度
        thread{
            while(true){
                Thread.sleep(1000)
                    seekBar.max = mediaPlayer.duration
                    seekBar.progress = mediaPlayer.currentPosition

            }
        }

        Play.setOnClickListener {
            play()
        }
        PAUSE.setOnClickListener {
            if(isPause){
                isPause = false
            }else{
                mediaPlayer.pause()
                isPause = true
            }
        }
        STOP.setOnClickListener {
            mediaPlayer.stop()
        }
        NEXT.setOnClickListener {
            current++
            if (musicList != null) {
                if(current >= musicList.size){
                    current = 0
                }
            }
            play()
        }
        LAST.setOnClickListener {
            current--
            if(current < 0){
                if (musicList != null) {
                    current = musicList.size - 1
                }
            }
            play()
        }
    }
    fun play(){
        if (musicList != null) {
            if (musicList.size == 0) return
        }
        val path = musicList?.get(current)
        mediaPlayer.reset()
        try{
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            musicName.text = musicNameList[current]
            count.text = "${current+1}/${musicList.size}"
        } catch (e: IOException){
            e.printStackTrace()
        }

        //放送通知
        val notificationManager = this.activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Channel_ID, "test",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this.context, Channel_ID)
        }else {
            builder = Notification.Builder(this.context)
        }

        val intent = Intent(this.activity, musicFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this.context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val notification = builder.setSmallIcon(R.drawable.ic_notify)
            .setContentTitle("自制音乐播放器")
            .setContentText("正在播放-${musicNameList?.get(current)}")
            .setContentIntent(pendingIntent)//点击通知跳转应用
            .setAutoCancel(true)//点击后自动清空
            .build()

        notificationManager.notify(Notification_ID, notification)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.getMusicList()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}