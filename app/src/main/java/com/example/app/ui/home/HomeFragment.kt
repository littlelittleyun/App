package com.example.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
       // val root = inflater.inflate(R.layout.fragment_home, container, false)
//val textView: TextView = root.findViewById(R.id.text_home)
        var seconds = 0

        homeViewModel.seconds.observe(viewLifecycleOwner, Observer {
            seconds = it
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60
            textView3.text = String.format("%02d:%02d:%02d",hours,minutes,secs)

        })

        start.setOnClickListener {
            homeViewModel.start()
        }
        stop.setOnClickListener {
            homeViewModel.stop()
        }
        restart.setOnClickListener {
            homeViewModel.reset()
        }
    }


}

