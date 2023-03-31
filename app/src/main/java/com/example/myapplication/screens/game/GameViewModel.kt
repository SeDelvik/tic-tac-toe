package com.example.myapplication.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel(size:Int,name1:String,name2:String) : ViewModel() {
//    нужна таблица, проверка что кто то выиграл, слежение за тем кто сейчас ходит
    var gameTable = mutableListOf<MutableList<Int>>()


    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    private var _isFirstPlayerTurn = MutableLiveData<Boolean>()
    val isFirstPlayerTurn: LiveData<Boolean>
        get()  =_isFirstPlayerTurn

    private var _name1 = MutableLiveData<String>()
    val name1: LiveData<String>
        get()  =_name1

    private var _name2 = MutableLiveData<String>()
    val name2: LiveData<String>
        get()  =_name2

    init {
        _size.value = size
        _name1.value = name1
        _name2.value = name2
        _isFirstPlayerTurn.value = true

        createArray()
    }

    private fun createArray(){
        for (i in 0 until _size.value!!){
            var row = mutableListOf<Int>()
            gameTable.add(row)
            for (j in 0 until _size.value!!){
                row.add(0)
            }
        }
    }

    fun newMove(i:Int, j:Int): Boolean {
        Log.i("PLAYER",_isFirstPlayerTurn.value.toString())
        var checkElem = 1
        if(_isFirstPlayerTurn.value == false) {
            checkElem = 2
        }
        if(gameTable[i][j] == 0){
            gameTable[i][j] = checkElem
            _isFirstPlayerTurn.value = !_isFirstPlayerTurn.value!!
            return true
        }

        return false
    }

    fun checkWin(): Boolean {
        var checkElem = 1
        if(_isFirstPlayerTurn.value == false){
         checkElem = 2
        }
        for (i in 0 until _size.value!!-1){
            for (j in 0 until _size.value!!){
                if(gameTable[i][j] != checkElem){
                    continue
                }
                if (checkRight(checkElem,i,j) || checkRightBottom(checkElem,i,j) ||
                    checkBottom(checkElem,i,j) || checkLeftBottom(checkElem,i,j)){
                    Log.i("WIN","IS WIN!!!")
                    return true
                }
            }
        }
        return false
    }

    private fun checkRight(elem:Int,i:Int,j:Int): Boolean{
        var count = 0
        var step = j
        while(step<_size.value!!){
            if(gameTable[i][step] != elem){
                break
            }
            count++
            step++
        }
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false

    }

    private fun checkRightBottom(elem:Int,i:Int,j:Int):Boolean{
        var count = 0
        var stepi = i
        var stepj = j
        while(stepi<_size.value!! && stepj<_size.value!!){
            if(gameTable[stepi][stepj] != elem){
                break
            }
            count++
            stepi++
            stepj++
        }
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }

    private fun checkBottom(elem:Int,i:Int,j:Int):Boolean{
        var count = 0
        var stepi = i
        while(stepi<_size.value!!){
            if(gameTable[stepi][j] != elem){
                break
            }
            count++
            stepi++
        }
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }

    private fun checkLeftBottom(elem:Int,i:Int,j:Int):Boolean{
        var count = 0
        var stepi = i
        var stepj = j
        while(stepi<_size.value!! && stepj >= 0){
            if(gameTable[stepi][stepj] != elem){
                break
            }
            count++
            stepi++
            stepj--
        }
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }
}