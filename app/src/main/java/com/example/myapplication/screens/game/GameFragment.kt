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
import androidx.navigation.Navigation
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
                                                requireArguments().getString("name1","Player1"),
                                                requireArguments().getString("name2","Player2"),
                                                requireArguments().getBoolean("againstRobot"))
        viewModel = ViewModelProvider(this,viewModelFactory).get(GameViewModel::class.java)

        val params: LinearLayout.LayoutParams =
            TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(15, 15, 15, 15)

        binding.textView.text = viewModel.name1.value
        binding.textView2.text = viewModel.name2.value


        Log.i("TEST", requireArguments().getInt("TestVar").toString())

        for (i in 0 until viewModel.size.value!!){
            val tableRow = TableRow(context)
            tableRow.gravity = Gravity.CENTER
            binding.tableLayout.addView(tableRow)
            for (j in 0 until viewModel.size.value!!){
                val button = Button(context)
                //button.layoutParams = params
                button.text = viewModel.gameTable.value!![i][j]
                button.textSize = 40.0F

                button.setOnClickListener {
                    var checkElem = "O"
                    if(viewModel.isFirstPlayerTurn.value == false) {
                        checkElem = "X"
                    }
                    if (viewModel.newMove(i,j)){
                       (it as Button).text = checkElem
                        if(viewModel.checkWin(i,j) || viewModel.checkDraw()){
//                            var bundle = Bundle()
//                            bundle.putSerializable("gameTable",viewModel.gameTable.value.toList())

                            Navigation.findNavController(it).navigate(R.id.action_gameFragment_to_endGameFragment,viewModel.getBundle())
                        }
                        if(viewModel.againstRobot.value!!){
                            var array = viewModel.robotTurn()
                            var button = getButton(array[0],array[1])
                            button.text = "X"
                        }
                    }
                }
                tableRow.addView(button)
            }
        }



        return binding.root
    }

    private fun getButton(i:Int,j:Int):Button{
       return (binding.tableLayout.getChildAt(i) as TableRow).getChildAt(j) as Button
    }

}