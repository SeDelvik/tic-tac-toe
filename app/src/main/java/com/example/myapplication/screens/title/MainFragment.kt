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



        binding.checkBox.setOnClickListener {
            viewModel._withRobot.value = !viewModel._withRobot.value!!
            enabledDisabledField()
        }

        setValues()

        binding.button.setOnClickListener {
            var num = 0
            try{
                num = binding.editTextNumberSigned.text.toString().toInt()
            } catch (_: NumberFormatException){}

            if(viewModel.isOk(binding.checkBox.isChecked,binding.textPersonName1.text.toString(),
                    binding.textPersonName2.text.toString(),
                    num)){
                val bundle = Bundle()
                bundle.putInt("size",num)
                bundle.putString("name1",binding.textPersonName1.text.toString())
                bundle.putBoolean("againstRobot",binding.checkBox.isChecked)
                if (binding.checkBox.isChecked){
                    bundle.putString("name2","Robot")
                }else{
                    bundle.putString("name2",binding.textPersonName2.text.toString())
                }
                Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_gameFragment,bundle)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        var num = 0
        try{
            num = binding.editTextNumberSigned.text.toString().toInt()
        } catch (_: NumberFormatException){}
        var name1 = binding.textPersonName1.text.toString()
        var name2 = binding.textPersonName2.text.toString()
        var isBot = binding.checkBox.isChecked
        viewModel.setParams(name1,name2,num,isBot)
    }

    private fun enabledDisabledField(){
        if (viewModel._withRobot.value!!){
            binding.textPersonName2.isFocusable = false
        }else{
            binding.textPersonName2.isFocusableInTouchMode = true
        }
    }

    private fun setValues(){
        binding.textPersonName1.setText(viewModel.name1.value)
        binding.textPersonName2.setText(viewModel.name2.value)
        binding.editTextNumberSigned.setText(viewModel.size.value.toString())
        binding.checkBox.isChecked = viewModel._withRobot.value!!
        enabledDisabledField()
    }
}