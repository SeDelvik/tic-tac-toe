package com.example.myapplication.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentStartBinding>(inflater,
            R.layout.fragment_start,container,false)

        binding.button2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_startFragment_to_mainFragment)
        }

        return binding.root
    }

}