package com.example.myapplication.screens.game

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModelFactory: GameViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game,container,false)

        viewModelFactory = GameViewModelFactory(requireArguments().getInt("size"),
                                                requireArguments().getString("name1","Player"),
                                                requireArguments().getString("name2","Robot"))
        viewModel = ViewModelProvider(this,viewModelFactory).get(GameViewModel::class.java)

        val params: LinearLayout.LayoutParams =
            TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(15, 15, 15, 15)

        binding.textView.text = viewModel.name1.value
        binding.textView2.text = viewModel.name2.value


        Log.i("TEST", requireArguments().getInt("TestVar").toString())

        var col = 3
        var row = 3

        for (i in 0 until row){
            val tableRow = TableRow(context)
            tableRow.gravity = Gravity.CENTER
            binding.tableLayout.addView(tableRow)
            for (j in 0 until col){
                var newText = ""
                when (viewModel.gameTable.value!![i][j]){
                    1 -> newText = "O"
                    2 -> newText = "X"
                }
                val button = Button(context)
                button.layoutParams = params
                button.text = newText //""
                button.textSize = 40.0F

                button.setOnClickListener {
                    Log.i("TEST","$i $j")
                    var checkElem = "O"
                    if(viewModel.isFirstPlayerTurn.value == false) {
                        checkElem = "X"
                    }
                    if (viewModel.newMove(i,j)){
                       (it as Button).text = checkElem
                        var test = viewModel.checkWin(i,j) //считает победу с задержкой
                        Log.i("IS WIN",test.toString())
                    }
//                    else{
//                        Log.i("FUCK","всё плохо")
//                    }
                }
                tableRow.addView(button)
            }
        }



        return binding.root
    }

}