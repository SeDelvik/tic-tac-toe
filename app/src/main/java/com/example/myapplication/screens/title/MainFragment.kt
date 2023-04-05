package com.example.myapplication.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main, container, false
        )

        viewModelFactory = MainViewModelFactory(3, false, "Player1", "Player2")
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.checkBox.setOnClickListener {
            viewModel._withRobot.value = !viewModel._withRobot.value!!
            enabledDisabledField()
        }

        setValues()

        binding.button.setOnClickListener {
            var num = 0
            try {
                num = binding.editTextNumberSigned.text.toString().toInt()
            } catch (_: NumberFormatException) {
            }
            if (viewModel.isOk(
                    binding.checkBox.isChecked, binding.textPersonName1.text.toString(),
                    binding.textPersonName2.text.toString(),
                    num
                )
            ) {
                val bundle = createBundle()
                Navigation.findNavController(it)
                    .navigate(R.id.action_mainFragment_to_gameFragment, bundle)
            }
        }
        return binding.root
    }


    override fun onDestroy() {
        //Метод используется для сохранения содержимого полей при повороте экрана.
        super.onDestroy()
        var num = 0
        try {
            num = binding.editTextNumberSigned.text.toString().toInt()
        } catch (_: NumberFormatException) {
        }
        val name1 = binding.textPersonName1.text.toString()
        val name2 = binding.textPersonName2.text.toString()
        val isBot = binding.checkBox.isChecked
        viewModel.setParams(name1, name2, num, isBot)
    }

    /**
     * Включает или выключает возможность редактирования имени второго игрока если отключена
     * или выключена опция "игра против робота" соответственно.
     * */
    private fun enabledDisabledField() {
        if (viewModel._withRobot.value!!) {
            binding.textPersonName2.isFocusable = false
        } else {
            binding.textPersonName2.isFocusableInTouchMode = true
        }
    }

    /**
     * Устанавливает значения, содержащиеся в viewModel, в текстовые поля во View.
     * */
    private fun setValues() {
        binding.textPersonName1.setText(viewModel.name1.value)
        binding.textPersonName2.setText(viewModel.name2.value)
        binding.editTextNumberSigned.setText(viewModel.size.value.toString())
        binding.checkBox.isChecked = viewModel._withRobot.value!!
        enabledDisabledField() // используется для проверки отключения редактирования имени второго игрока
    }

    /**
     * Создает бандл для следующего экрана.
     * Бандл содержжит: размер поля, имена игроков, игра ли с роботом.
     * */
    private fun createBundle(): Bundle {
        val bundle = Bundle()
        var num = 0
        try {
            num = binding.editTextNumberSigned.text.toString().toInt()
        } catch (_: NumberFormatException) {
        }
        bundle.putInt("size", num)
        bundle.putString("name1", binding.textPersonName1.text.toString())
        bundle.putBoolean("againstRobot", binding.checkBox.isChecked)
        if (binding.checkBox.isChecked) {
            bundle.putString("name2", getString(R.string.robot))
        } else {
            bundle.putString("name2", binding.textPersonName2.text.toString())
        }
        return bundle
    }
}