package com.example.app.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.ui.Weather.City
import com.example.app.ui.dashboard.model.CardMatchingGame
import java.io.ObjectInputStream

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    private val _game:MutableLiveData<CardMatchingGame> = MutableLiveData()
    //传递数据
    val game: LiveData<CardMatchingGame> = _game

    init {
       var game = loadData()
        if (game != null) {
            _game.postValue(game)
        }else{
            game = CardMatchingGame(24)
            _game.postValue(game)
        }
    }


    public fun loadData(): CardMatchingGame? {
        try {
            //val x = getApplication<Application>().openFileInput()
            val input =getApplication<Application>().openFileInput(gameFile)
            val objectInputStream =  ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input?.close()
            return game
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


}