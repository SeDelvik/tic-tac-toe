package com.example.myapplication.screens.endGame

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEndGameBinding
import com.example.myapplication.databinding.FragmentGameBinding
import com.example.myapplication.databinding.FragmentMainBinding


class EndGameFragment : Fragment() {

    private lateinit var binding: FragmentEndGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.i("TestBundle",
            (requireArguments().getSerializable("gameTable") as List<List<String>>).toString()
        )

        binding = DataBindingUtil.inflate<FragmentEndGameBinding>(inflater,
            R.layout.fragment_end_game,container,false)

        var list = requireArguments().getSerializable("gameTable") as List<List<String>>
        var winner = requireArguments().getString("winner")

        binding.textViewWinner.text = winner

        for (i in 0 until list.size){
            val tableRow = TableRow(context)
            tableRow.gravity = Gravity.CENTER
            binding.tableLayoutWinner.addView(tableRow)
            for (j in 0 until list.size){
                val button = Button(context)
                button.text = list[i][j]
                button.textSize = 40.0F
                tableRow.addView(button)
            }
        }


        return binding.root
    }

}