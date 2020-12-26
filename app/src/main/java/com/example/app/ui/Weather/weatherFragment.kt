package com.example.app.ui.Weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.app.R
import com.example.app.weatherActivity
import kotlinx.android.synthetic.main.weather_fragment.*


class weatherFragment : Fragment() {

    companion object {
        fun newInstance() = weatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(WeatherViewModel::class.java)
        viewModel=ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.cities.observe(requireActivity(), Observer {
            val cities = it
            val adapter = getActivity()?.let { it1 ->
                ArrayAdapter(
                    it1,
                    android.R.layout.simple_list_item_1,
                    cities
                )
            }
            //val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,cities)
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                val cityCode = cities[position].city_code
                //myListener?.sendValue(cityCode)
                transfer()


                //startActivityForResult(Intent(context, weatherActivity::class.java), 1)


                //val intent = Intent(this,weatherFragment::class.java)
                //intent.putExtra("city_code",cityCode)
                //startActivity(intent)

            }
        })
    }

    fun transfer(){
        //跳转页面
        val intent = Intent(context, weatherActivity::class.java)
        //val intent2 = Intent(context.)
        val cityCode = "101030100"
        //传递参数
        val bundle = Bundle()
        bundle.putString("citycode", cityCode)
        intent.putExtras(bundle)
        startActivity(intent)
    }



}




