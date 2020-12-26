package com.example.app

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app.weather.Forecast
import com.example.app.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.weather_fragment2.*


class weatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val intent = intent
        val bundle = intent.extras
        val cityCode = bundle!!.getString("citycode")

        val baseURL = "http://t.weather.itboy.net/api/weather/city/"

        //val cityCode = intent.getStringExtra("city_code")
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(baseURL + cityCode, {
            val gson = Gson()
            val WeatherType = object : TypeToken<Weather>() {}.type
            val weather = gson.fromJson<Weather>(it, WeatherType)

            city.text = weather.cityInfo.city
            province.text = weather.cityInfo.parent
            wendu.text = "温度：" + weather.data.wendu
            shidu.text = "湿度:" + weather.data.shidu
            val firstDay = weather.data.forecast.first()
            when (firstDay.type) {
                "晴" -> imageView.setImageResource(R.drawable.sun)
                "阴" -> imageView.setImageResource(R.drawable.cloud)
                "多云" -> imageView.setImageResource(R.drawable.mcloud)
                "阵雨" -> imageView.setImageResource(R.drawable.rain)
                else -> imageView.setImageResource(R.drawable.thunder)
            }
            val adapter = ArrayAdapter<Forecast>(
                this,
                android.R.layout.simple_list_item_1,
                weather.data.forecast
            )
            ListView.adapter = adapter


            Log.d("MainActivity", "${weather.cityInfo.city} ${weather.cityInfo.parent}")
        }, {
            Log.d("MainActivity", "$it")
        })
        queue.add(stringRequest)






    }
}