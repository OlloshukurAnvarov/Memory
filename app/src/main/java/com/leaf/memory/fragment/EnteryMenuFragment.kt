package com.leaf.memory.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentEnteryMenuBinding

class EnteryMenuFragment : Fragment(R.layout.fragment_entery_menu) {
    private val binding: FragmentEnteryMenuBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}