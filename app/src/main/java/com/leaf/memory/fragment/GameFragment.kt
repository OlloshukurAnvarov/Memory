package com.leaf.memory.fragment

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.MakeCards
import com.leaf.memory.R
import com.leaf.memory.adapter.CardAdapter
import com.leaf.memory.databinding.FragmentGameBinding
import com.leaf.memory.preferences.Settings

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private val settings by lazy { Settings(requireContext()) }
    private val images by lazy { MakeCards().cards(settings.level()) }
    private val adapter by lazy { CardAdapter(requireContext(), images) }
    private val cardsGrid: GridLayout by lazy { binding.cardsGrid }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadData()
        loadDataToViews()
    }

    private fun loadViews() {
        val level = settings.level()
        if (level == 32)
            cardsGrid.apply {
                rowCount = 4
                columnCount = 8
            }
        else
            cardsGrid.apply {
                rowCount = 2
                columnCount = level / 2
            }
    }

    private fun loadData() {
        for (i in 0 until cardsGrid.rowCount) {
            for (j in 0 until cardsGrid.columnCount) {
                val index = i * cardsGrid.columnCount + j
                val view = adapter.getView(index, null, cardsGrid)
                cardsGrid.addView(view)
            }
        }
    }

    private fun loadDataToViews() {

        binding.back.setOnClickListener {
            //Firdavs
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }
}