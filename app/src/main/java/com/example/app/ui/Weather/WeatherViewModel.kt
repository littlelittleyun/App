package com.example.app.ui.Weather


import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    //内部数据
    private val _cities:MutableLiveData<List<City>> = MutableLiveData()
    //传递数据
    val cities: LiveData<List<City>> = _cities

    init {
        thread {
            val str = readFileFromRaw(R.raw.citycode)
            val gson = Gson()
            val CityType  = object : TypeToken<List<City>>(){}.type
            var cts:List<City> = gson.fromJson(str,CityType)
            cts = cts.filter { it.city_code != ""}
            _cities.postValue(cts)
        }
    }
    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(getApplication<Application>().resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}