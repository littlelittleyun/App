package com.example.app.ui.dashboard

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.WeatherActivity
import com.example.app.ui.dashboard.model.CardMatchingGame
import kotlinx.android.synthetic.main.weather_fragment.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

const val gameFile = "gameFile"
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    companion object {
        lateinit var game: CardMatchingGame
    }
    val cardButtons = mutableListOf<Button>()
    lateinit var adapter:CardRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recylerView = view.findViewById<RecyclerView>(R.id.recylerView1)
        val reset = view.findViewById<Button>(R.id.reset)

        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        dashboardViewModel.game.observe(requireActivity(), {
            game = it
            fun updateUI() {
                adapter.notifyDataSetChanged()
                val score = view?.findViewById<TextView>(R.id.score)
                score?.text = String.format("%s%d", getString(R.string.score), Companion.game.score)
                score?.text = getString(R.string.score) + Companion.game.score
            }

            adapter = CardRecyclerViewAdapter(game)
            recylerView.adapter = adapter
            val configuration = resources.configuration
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recylerView.layoutManager = GridLayoutManager(activity, 6)
            }else{
                val gridLayoutManager = GridLayoutManager(activity, 4)
                recylerView.layoutManager = gridLayoutManager
            }
            updateUI()
            adapter.setOnCardClickListener {
                game.chooseCardAtIndex(it)
                updateUI()
            }

            reset.setOnClickListener {
                game.reset()
                updateUI()
            }


        })


    }



    override fun onStop() {
        super.onStop()
        saveData()
    }

    fun saveData() {
        try {
            val output = activity?.openFileOutput(gameFile, AppCompatActivity.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(output)
            objectOutputStream.writeObject(game)
            objectOutputStream.close()
            output?.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


