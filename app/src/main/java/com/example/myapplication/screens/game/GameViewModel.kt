package com.example.myapplication.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel(size: Int, name1: String, name2: String, againstRobot: Boolean) : ViewModel() {
    private var _isWin = MutableLiveData<Boolean>(false)
    val win: LiveData<Boolean>
        get() = _isWin

    private var _gameTable = MutableLiveData<MutableList<MutableList<String>>>()
    val gameTable: LiveData<MutableList<MutableList<String>>>
        get() = _gameTable

    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    private var _isFirstPlayerTurn = MutableLiveData<Boolean>()
    val isFirstPlayerTurn: LiveData<Boolean>
        get() = _isFirstPlayerTurn

    private var _name1 = MutableLiveData<String>()
    val name1: LiveData<String>
        get() = _name1

    private var _name2 = MutableLiveData<String>()
    val name2: LiveData<String>
        get() = _name2

    private var _againstRobot = MutableLiveData<Boolean>()
    val againstRobot: LiveData<Boolean>
        get() = _againstRobot

    init {
        _size.value = size
        _name1.value = name1
        _name2.value = name2
        _isFirstPlayerTurn.value = true
        _againstRobot.value = againstRobot

        _gameTable.value = mutableListOf<MutableList<String>>()
        createArray()
    }

    /**
     * Инициализирует массив для хранения данных о игровом поле*/
    private fun createArray() {
        for (i in 0 until _size.value!!) {
            val row = mutableListOf<String>()
            _gameTable.value?.add(row)
            for (j in 0 until _size.value!!) {
                row.add("")
            }
        }
    }

    /**
     * Отмечает ход игрока в таблице по переданным аргументам.
     *
     * @return true, если ячейка не занята (""). В противном случае false.
     * */
    fun newMove(i: Int, j: Int): Boolean {
        var checkElem = "O"
        if (_isFirstPlayerTurn.value == false) {
            checkElem = "X"
        }
        if (_gameTable.value!![i][j] == "") {
            _gameTable.value!![i][j] = checkElem
            _isFirstPlayerTurn.value = !_isFirstPlayerTurn.value!!
            return true
        }
        return false
    }

    /**
     * Проверяет выигрыш относительно переданного места в таблие.*/
    fun checkWin(i: Int, j: Int): Boolean {
        var checkElem = "O"
        if (_isFirstPlayerTurn.value == true) {
            checkElem = "X"
        }
        if (checkLefRight(checkElem, i, j) || checkTopBottom(checkElem, i, j) ||
            checkDiagonalLeftRight(checkElem, i, j) || checkDiagonalRightLeft(checkElem, i, j)
        ) {
            _isWin.value = true
            return true
        }
        return false
    }

    /**
     * Проверяет ничью (невозможно сделать новый ход).*/
    fun checkDraw(): Boolean {
        for (row in _gameTable.value!!) {
            if ("" in row) {
                return false
            }
        }
        return true
    }

    /**
     * Делает автоматический ход (ставит в любое пустое место)*/

    fun robotTurn(): Array<Int> {
        var i = 0
        var j = 0
        while (true) {
            i = (0 until _size.value!!).random()
            j = (0 until _size.value!!).random()
            if (_gameTable.value!![i][j] == "") {
                break
            }
        }
        _isFirstPlayerTurn.value = !_isFirstPlayerTurn.value!!
        _gameTable.value!![i][j] = "X"
        return arrayOf(i, j)
    }

    /**
     * Проверяет выиигрыш по диагонали (с верхнего левого в нижний правый угол).*/
    private fun checkDiagonalRightLeft(elem: String, i: Int, j: Int): Boolean {
        var count = 0
        var stepi = i
        var stepj = j
        while (stepi >= 0 && stepj >= 0) {
            if (_gameTable.value!![stepi][stepj] != elem) {
                break
            }
            count++
            stepi--
            stepj--
        }
        stepi = i
        stepj = j
        while (stepi < _size.value!! && stepj < _size.value!!) {
            if (_gameTable.value!![stepi][stepj] != elem) {
                break
            }
            count++
            stepi++
            stepj++
        }
        count--
        if ((_size.value!! >= 6 && count >= 5) || (_size.value!! <= 5 && count == 3)) {
            return true
        }
        return false
    }

    /**
     * Проверяет выиигрыш по диагонали (с верхнего правого в нижний левый угол).*/
    private fun checkDiagonalLeftRight(elem: String, i: Int, j: Int): Boolean {
        // проверка деагонали сверхнего правого до левого нижнего угла
        var count = 0
        var stepi = i
        var stepj = j
        while (stepi >= 0 && stepj < _size.value!!) {
            if (_gameTable.value!![stepi][stepj] != elem) {
                break
            }
            count++
            stepi--
            stepj++
        }
        stepi = i
        stepj = j
        while (stepi < _size.value!! && stepj >= 0) {
            if (_gameTable.value!![stepi][stepj] != elem) {
                break
            }
            count++
            stepi++
            stepj--
        }
        count--
        if ((_size.value!! >= 6 && count >= 5) || (_size.value!! <= 5 && count == 3)) {
            return true
        }
        return false
    }

    /**
     * Проверка горизонтали*/
    private fun checkLefRight(elem: String, i: Int, j: Int): Boolean {
        // проверка слева на право
        var count = 0
        var stepj = j
        while (stepj < _size.value!!) {
            if (_gameTable.value!![i][stepj] != elem) {
                break
            }
            count++
            stepj++
        }
        stepj = j
        while (stepj >= 0) {
            if (_gameTable.value!![i][stepj] != elem) {
                break
            }
            count++
            stepj--
        }
        count--
        if ((_size.value!! >= 6 && count >= 5) || (_size.value!! <= 5 && count == 3)) {
            return true
        }
        return false
    }

    /**
     * Проверка вертикали*/
    private fun checkTopBottom(elem: String, i: Int, j: Int): Boolean {
        // проверка сверху вниз
        var count = 0
        var stepi = i
        while (stepi < _size.value!!) {
            if (_gameTable.value!![stepi][j] != elem) {
                break
            }
            count++
            stepi++
        }
        stepi = i
        while (stepi >= 0) {
            if (_gameTable.value!![stepi][j] != elem) {
                break
            }
            count++
            stepi--
        }
        count--
        if ((_size.value!! >= 6 && count >= 5) || (_size.value!! <= 5 && count == 3)) {
            return true
        }
        return false
    }

}