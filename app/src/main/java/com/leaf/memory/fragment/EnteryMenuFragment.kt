package com.leaf.memory.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.PlayerService
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentEnteryMenuBinding
import com.leaf.memory.preferences.Settings

class EnteryMenuFragment : Fragment(R.layout.fragment_entery_menu) {
    private val binding: FragmentEnteryMenuBinding by viewBinding()
    private val settings by lazy { Settings.getData(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (settings.level() > 4)
            binding.continueButton.visibility = View.VISIBLE
        else
            binding.continueButton.visibility = View.GONE

        binding.newGame.setOnClickListener {
            settings.saveLevel(4)
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment())
                .remove(this)
                .commit()
        }

        binding.continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, GameFragment())
                .remove(this)
                .commit()
        }

        binding.exit.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        if (settings.bool()) {
            binding.volume.setImageResource(R.drawable.unmute_icon)
            requireContext().startService(Intent(requireContext(), PlayerService::class.java))
        }

        binding.apply {
            volume.setOnClickListener {
                if (settings.bool()) {
                    requireContext().stopService(Intent(requireContext(), PlayerService::class.java))
                    volume.setImageResource(R.drawable.mute_icon)
                    settings.saveBool(false)
                }
                else {
                    requireContext().startService(Intent(requireContext(), PlayerService::class.java))
                    volume.setImageResource(R.drawable.unmute_icon)
                    settings.saveBool(true)
                }
            }
        }
    }
}