package com.example.myapplication.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.screens.game.GameViewModel
import com.example.myapplication.screens.game.GameViewModelFactory

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater,
            R.layout.fragment_main,container,false)

        viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        val bundle = Bundle()
        bundle.putInt("size",3)
        bundle.putString("name1","Name1")
        bundle.putString("name2","Name2")

        binding.checkBox.setOnClickListener {
            if (viewModel.withRobot.value!!) //todo изменение отображения текстового поля для второго игрока
        }

        binding.button.setOnClickListener {
            var num = 0
            try{
                num = binding.editTextNumberSigned.text.toString().toInt()
            } catch (_: NumberFormatException){}

            if(viewModel.isOk(binding.checkBox.isChecked,binding.textPersonName1.text.toString(),
                    binding.textPersonName2.text.toString(),
                    num)){
                Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_gameFragment,bundle)
            }
        }
        return binding.root
    }

}