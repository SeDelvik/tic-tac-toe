package com.example.myapplication.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel(size:Int,name1:String,name2:String) : ViewModel() {
//   Todo переехать на строки
    private var _gameTable = MutableLiveData<MutableList<MutableList<Int>>>() // mutableListOf<MutableList<Int>>()
    val gameTable: LiveData<MutableList<MutableList<Int>>>
        get() = _gameTable

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

        _gameTable.value = mutableListOf<MutableList<Int>>()
        createArray()
    }

    private fun createArray(){
        for (i in 0 until _size.value!!){
            var row = mutableListOf<Int>()
            _gameTable.value?.add(row)
            for (j in 0 until _size.value!!){
                row.add(0)
            }
        }

        Log.i("values",_gameTable.value.toString())
    }

    fun newMove(i:Int, j:Int): Boolean {
        Log.i("PLAYER",_isFirstPlayerTurn.value.toString())
        var checkElem = 1
        if(_isFirstPlayerTurn.value == false) {
            checkElem = 2
        }
        if(_gameTable.value!![i][j] == 0){
            _gameTable.value!![i][j]/*[i][j]*/ = checkElem
            _isFirstPlayerTurn.value = !_isFirstPlayerTurn.value!!
            return true
        }

        return false
    }

    fun checkWin(i:Int,j:Int): Boolean {
        var checkElem = 1
        if(_isFirstPlayerTurn.value == true /*== false*/){
         checkElem = 2
        }
        return (checkLefRight(checkElem,i,j) || checkTopBottom(checkElem,i,j) ||
                checkDiagonalLeftRight(checkElem,i,j) || checkDiagonalRightLeft(checkElem,i,j))
    }

    private fun checkDiagonalRightLeft(elem:Int,i:Int,j:Int):Boolean{
        // проверка деагонали с верхнего правого до левого нижнего угла
        var count = 0
        var stepi = i
        var stepj = j
        while (stepi >= 0 && stepj >= 0){
            if (_gameTable.value!![stepi][stepj]  != elem){
                break
            }
            count++
            stepi--
            stepj--
        }
        stepi = i
        stepj = j
        while (stepi < _size.value!! && stepj < _size.value!!){
            if (_gameTable.value!![stepi][stepj] != elem){
                break
            }
            count++
            stepi++
            stepj++
        }
        count--
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }

    private fun checkDiagonalLeftRight(elem:Int,i:Int,j:Int):Boolean{
        // проверка деагонали сверхнего правого до левого нижнего угла
        var count = 0
        var stepi = i
        var stepj = j
        while (stepi >= 0 && stepj < _size.value!!){
            if (_gameTable.value!![stepi][stepj] != elem){
                break
            }
            count++
            stepi--
            stepj++
        }
        stepi = i
        stepj = j
        while (stepi < _size.value!! && stepj >= 0){
            if (_gameTable.value!![stepi][stepj] != elem){
                break
            }
            count++
            stepi++
            stepj--
        }
        count--
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }
    private fun checkLefRight(elem:Int,i:Int,j:Int):Boolean{
        // проверка слева на право
        var count = 0
        var stepj = j
        while (stepj < _size.value!!){
            if (_gameTable.value!![i][stepj] != elem){
                break
            }
            count++
            stepj++
        }
        stepj = j
        while ( stepj >= 0){
            if (_gameTable.value!![i][stepj] != elem){
                break
            }
            count++
            stepj--
        }
        count--
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }

    private fun checkTopBottom(elem:Int,i:Int,j:Int):Boolean{
        // проверка сверху вниз
        var count = 0
        var stepi = i
        while (stepi < _size.value!!){
            if (_gameTable.value!![stepi][j] != elem){
                break
            }
            count++
            stepi++
        }
        stepi = i
        while ( stepi >= 0){
            if (_gameTable.value!![stepi][j] != elem){
                break
            }
            count++
            stepi--
        }
        count--
        if((_size.value!! >= 6 && count >= 5)||(_size.value!! <= 5 && count == 3)){
            return true
        }
        return false
    }
}