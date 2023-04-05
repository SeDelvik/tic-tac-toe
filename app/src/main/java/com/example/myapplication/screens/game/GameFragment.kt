package com.example.myapplication.screens.game

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
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
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game, container, false
        )

        viewModelFactory = GameViewModelFactory(
            requireArguments().getInt("size"),
            requireArguments().getString("name1", "Player1"),
            requireArguments().getString("name2", "Player2"),
            requireArguments().getBoolean("againstRobot")
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        val params: LinearLayout.LayoutParams =
            TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(15, 15, 15, 15)

        binding.textView.text = viewModel.name1.value
        binding.textView2.text = viewModel.name2.value

        for (i in 0 until viewModel.size.value!!) {
            val tableRow = TableRow(context)
            tableRow.gravity = Gravity.CENTER
            binding.tableLayout.addView(tableRow)
            for (j in 0 until viewModel.size.value!!) {
                var button = createButton(i, j)
                tableRow.addView(button)
            }
        }

        return binding.root
    }

    /**
     * Создает кнопку для вставки ее по указанному месту в TableView.
     *
     * @return Кнопка для вставки.
     */
    private fun createButton(i: Int, j: Int): Button {
        val button = Button(context)
        button.text = viewModel.gameTable.value!![i][j]
        button.textSize = 40.0F

        button.setOnClickListener {
            var checkElem = "O"
            if (viewModel.isFirstPlayerTurn.value == false) {
                checkElem = "X"
            }
            if (viewModel.newMove(i, j)) {
                (it as Button).text = checkElem
                if (viewModel.checkWin(i, j) || viewModel.checkDraw()) {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_gameFragment_to_endGameFragment, getBundle())
                }
                if (viewModel.againstRobot.value!! && !viewModel.checkDraw()) {
                    val array = viewModel.robotTurn()
                    val button = getButton(array[0], array[1])
                    button.text = "X"
                }
            }
        }
        return button
    }

    /**
     * Возвращает кнопку по указанному месту в TableView
     *
     * @return Кнопка
     * */
    private fun getButton(i: Int, j: Int): Button {
        return (binding.tableLayout.getChildAt(i) as TableRow).getChildAt(j) as Button
    }

    /**
     * Собирает бандл для экрана конца игры. Содержит: итоговое поле, имя победителя ("ничья" - тоже имя).
     *
     * @return Бандл с состоянием игрового поля и именем победителя.
     */
    private fun getBundle(): Bundle {
        val bundle = Bundle()
        bundle.putSerializable("gameTable", viewModel.gameTable.value as java.io.Serializable)
        var winner = getString(R.string.draw)
        if (viewModel.win.value!!) {
            if (viewModel.isFirstPlayerTurn.value!!) {
                winner = viewModel.name2.value!!
            } else {
                winner = viewModel.name1.value!!
            }
        }
        bundle.putString("winner", winner)
        return bundle
    }

}