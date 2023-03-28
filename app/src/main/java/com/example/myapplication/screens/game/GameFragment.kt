package com.example.myapplication.screens.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGameBinding
import com.example.myapplication.databinding.FragmentMainBinding
import kotlin.math.log


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
            R.layout.fragment_game,container,false)

        // test
        var i = 0
        binding.testGameButton.setOnClickListener {
            val testTextView = TextView(context)
            testTextView.text = "test$i"
            testTextView.setOnClickListener{
                Log.i("TEST",testTextView.text.toString())
            }

            binding.gameFragmentTest.addView(testTextView)
            i++

        }


        //

        return binding.root
    }

}