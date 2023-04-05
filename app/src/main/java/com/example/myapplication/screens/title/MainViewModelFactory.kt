package com.example.myapplication.screens.title

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val size: Int, private val withRobot: Boolean,
    private val name1: String, private val name2: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(size, withRobot, name1, name2) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}