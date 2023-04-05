package com.example.myapplication.screens.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(size: Int, withRobot: Boolean, name1: String, name2: String) : ViewModel() {
    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    /*    private*/ var _withRobot = MutableLiveData<Boolean>()
//    val withRobot: LiveData<Boolean>
//        get()  =_withRobot

    private var _name1 = MutableLiveData<String>()
    val name1: LiveData<String>
        get() = _name1

    private var _name2 = MutableLiveData<String>()
    val name2: LiveData<String>
        get() = _name2

    init {
        _size.value = size //3
        _withRobot.value = withRobot //false
        _name1.value = name1 //"Player1"
        _name2.value = name2 //"Player2"
    }

    fun isOk(isRobot: Boolean, name1: String, name2: String, size: Int): Boolean {
        return (name1.isNotEmpty() && name2.isNotEmpty() && size > 2 && !isRobot) ||
                (name1.isNotEmpty() && size > 2 && isRobot)
    }

    fun setParams(name1: String, name2: String, size: Int, isRobot: Boolean) {
        _name1.value = name1
        _name2.value = name2
        _size.value = size
        _withRobot.value = isRobot
    }
}