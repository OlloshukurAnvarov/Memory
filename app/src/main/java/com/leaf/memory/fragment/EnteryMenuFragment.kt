package com.leaf.memory.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentEnteryMenuBinding
import com.leaf.memory.preferences.Settings

class EnteryMenuFragment : Fragment(R.layout.fragment_entery_menu) {
    private val binding: FragmentEnteryMenuBinding by viewBinding()
    private val settings by lazy { Settings.getData(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (settings.level() > 1)
            binding.continueButton.visibility = View.VISIBLE
        else
            binding.continueButton.visibility = View.GONE

        binding.newGame.setOnClickListener {
            settings.saveLevel(1)
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment())
                .addToBackStack("EnteryMenuFragment")
                .commit()
        }

        binding.continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment())
                .addToBackStack("EnteryMenuFragment")
                .commit()
        }

        binding.exit.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

}