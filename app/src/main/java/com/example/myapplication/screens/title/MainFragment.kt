package com.example.myapplication.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater,
            R.layout.fragment_main,container,false)
        binding.button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_gameFragment)
        }
        return binding.root
    }

}