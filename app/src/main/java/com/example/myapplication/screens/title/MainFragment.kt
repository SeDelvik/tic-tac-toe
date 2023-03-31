package com.example.myapplication.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater,
            R.layout.fragment_main,container,false)

        val bundle = Bundle()
        bundle.putInt("size",3)
        bundle.putString("name1","Name1")
        bundle.putString("name2","Name2")
//        findNavController().navigate(R.id.action_mainFragment_to_gameFragment,bundle)

        binding.button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_gameFragment,bundle)
        }
        return binding.root
    }

}