@file:Suppress("DEPRECATION")

package com.example.app.ui.Weather

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.app.R
import com.example.app.WeatherActivity
import kotlinx.android.synthetic.main.weather_fragment.*


class weatherFragment : Fragment() {

    companion object {
        //fun newInstance() = weatherFragment()
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
        viewModel.cities.observe(requireActivity(), {
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
                val intent = Intent(this.activity, WeatherActivity::class.java).also {
                    it.putExtra("city_code",cityCode)
                    startActivity(it)
                }
            }
        })
    }
}




