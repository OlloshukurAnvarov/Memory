package com.leaf.memory.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentDegreeBinding

class degree_fragment : Fragment(R.layout.fragment_degree){
    private val binding : FragmentDegreeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.easy.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment::class.java, bundleOf("mode" to 1))
                .commit()
        }
        binding.medium.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment::class.java, bundleOf("mode" to 2))
                .commit()
        }
        binding.hard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment::class.java, bundleOf("mode" to 3))
                .commit()
        }
    }
}