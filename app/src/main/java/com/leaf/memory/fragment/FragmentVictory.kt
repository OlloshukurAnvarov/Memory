package com.leaf.memory.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentVictoryBinding

class FragmentVictory : Fragment(R.layout.fragment_victory) {
    val binding : FragmentVictoryBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val  level = arguments?.getInt("level")!!
        binding.level.setText("LEVEL " + level.toString())
        binding.next.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment::class.java, bundleOf("level" to level+1))
                .setReorderingAllowed(true)
                .commit()
        }
        binding.retry.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, GameFragment::class.java, bundleOf("level" to level))
                .setReorderingAllowed(true)
                .commit()
        }
    }
}
