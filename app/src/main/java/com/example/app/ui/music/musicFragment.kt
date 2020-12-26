package com.example.app.ui.music

import android.content.*
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.app.R
import kotlinx.android.synthetic.main.music_fragment.*
import kotlin.concurrent.thread
const val MyReceiverAction = "com.example.app.ui.music.MusicService"
class musicFragment : Fragment(),ServiceConnection  {

    companion object {
        fun newInstance() = musicFragment()
    }

    private lateinit var viewModel: MusicViewModel

    val Channel_ID = "my channel"
    val Notification_ID = 1
    var binder: MusicService.MusicBinder?=null
   // lateinit var receiver: MusicReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)

      //  val intentFilter = IntentFilter()
      //  intentFilter.addAction(MyReceiverAction)
      //  receiver = MusicReceiver()
      //  registerReceiver(receiver,intentFilter)


/*
        //申请权限，更新时不用重复申请
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }else{
            // getMusicList(
            startMusicService()
        }

        //设置拖动条进度与歌曲播放同步
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekbar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    binder?.currentPosition = progress
                    //               mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        //每秒更新进度



*/

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }


/*


    fun startMusicService(){
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,1)
        startService(intent)
        bindService(intent,this,Context.BIND_AUTO_CREATE)
    }

    fun onPlay(v: View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,1)
        startService(intent)
    }
    fun onPause(v: View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,2)
       // startService(intent)
    }
    fun onStop(v: View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,3)
      //  startService(intent)
    }
    fun onNext(v: View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,4)
        //startService(intent)
    }
    fun onLast(v: View) {
        val intent = Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,5)
        //startService(intent)
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startMusicService()
        //getMusicList()
    }




    override fun onDestroy() {
        super.onDestroy()
        val intent=Intent(this,MusicService::class.java)
        //stopService()
        unregisterReceiver(receiver)
        unbindService(this)
        //  mediaPlayer.release()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        binder = service as MusicService.MusicBinder
        seekBar.max = binder!!.duration
        musicName.text = binder!!.musicName
        count.text = "${binder!!.currentIndex+1}/${binder!!.size}"

        thread{
            while(binder != null){
                Thread.sleep(1000)
                runOnUiThread {
                    seekBar.progress = binder!!.currentPosition
                }
            }
        }
    }
    inner class MusicReceiver:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val op = intent?.getIntExtra("operate", 0)
            if (op == 1) {
                seekBar.max = binder!!.duration
                musicName.text = binder!!.musicName
                count.text = "${binder!!.currentIndex + 1}/${binder!!.size}"

            }
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        binder = null
    }

 */
}