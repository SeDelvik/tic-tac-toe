package com.example.myapplication.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(private val size:Int, private val name1:String, private val name2:String)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(size,name1,name2) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}