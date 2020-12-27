package com.example.app.ui.music

import android.app.Activity
import android.app.Application
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.internal.ContextUtils.getActivity


class MusicViewModel(application: Application) : AndroidViewModel(application)  {


    val TAG = "MainActivity"
    private  var musicList1 = mutableListOf<String>()
    private val musicNameList1 = mutableListOf<String>()


    //内部数据
    private var _musicList: MutableLiveData<List<String>> = MutableLiveData()
    private var _musicNameList: MutableLiveData<List<String>> = MutableLiveData()
    //传递数据
    val musicList: LiveData<List<String>> = _musicList
    val musicNameList: LiveData<List<String>> = _musicNameList

   init {

       getMusicList()
       _musicList.postValue(musicList1)
       _musicNameList.postValue(musicNameList1)


   }


    //@SuppressLint("RestrictedApi")
    fun getMusicList(){
        //val x = con
        val resolver: ContentResolver = getApplication<Application>().getContentResolver()
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
                musicList1.add(musicPath)
                val musicName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList1.add(musicName)
                Log.d(TAG, "getMusicList: $musicPath name:$musicName")
            }
            cursor.close()
        }
    }



}