package com.example.app.ui.music

import android.content.ContentResolver
import android.media.MediaPlayer
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app.MainActivity


class MusicViewModel : ViewModel() {


    val TAG = "MainActivity"
    val mediaPlayer = MediaPlayer()
    val musicList = mutableListOf<String>()
    val musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false

    val Channel_ID = "my channel"
    val Notification_ID = 1

/*
    //@SuppressLint("RestrictedApi")
    private fun getMusicList(){
        //val x = con
        val resolver: ContentResolver = this.content.getContentResolver()
        //val resolver = ((MainActivity) getActivity() )?.contentResolver
        val cursor = resolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor != null){
            while (cursor.moveToNext()){
                val musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                val musicName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(TAG, "getMusicList: $musicPath name:$musicName")
            }
            cursor.close()
        }
    }

*/
    // TODO: Implement the ViewModel
}