package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.adapter.CardAdapter
import com.leaf.memory.databinding.FragmentGameBinding
import com.leaf.memory.model.Card
import com.leaf.memory.preferences.Settings
import java.util.Collections

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private val images = ArrayList<Card>()
    private val adapter by lazy { CardAdapter(requireContext(), images) }
    private var level = 1
    private lateinit var cardLayout: GridLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
        loadViews()
        loadDataToViews()
    }

    private fun loadDataToViews() {
        binding.back.setOnClickListener {
            //////
            ///// Firdavs
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun loadViews() {
        for (i in 0 until cardLayout.rowCount) {
            for (j in 0 until cardLayout.columnCount) {
                val cardView = cardLayout.getChildAt(i * cardLayout.columnCount + j) as FrameLayout
                cardView.tag = false
                adapter
                val image = cardView.getChildAt(0) as ImageView
                image.tag = images[i * cardLayout.columnCount + j]

                cardView.setOnClickListener {
                    cardView.isClickable = false
                    cardView.tag = true
                    flip_out(cardView, image, i, j)

                    for (iS in 0 until cardLayout.rowCount) {
                        for (jS in 0 until cardLayout.columnCount) {
                            val cardViewCheck =
                                cardLayout.getChildAt(iS * cardLayout.columnCount + jS) as FrameLayout
                            val imageCheck = cardViewCheck.getChildAt(0) as ImageView
                            if (i == iS && j == jS) {
                                continue
                            } else {
                                if (cardViewCheck.tag == true && image.tag != imageCheck.tag) {
                                    cardViewCheck.tag = false
                                    cardView.tag = false
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(p0: Long) {

                                        }

                                        override fun onFinish() {
                                            Toast.makeText(
                                                requireContext(),
                                                "False",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            flip_in(cardViewCheck, imageCheck)
                                            flip_in(cardView, image)
                                        }

                                    }.start()

                                } else if (cardViewCheck.tag == true && image.tag == imageCheck.tag) {
                                    cardViewCheck.tag = false
                                    cardView.tag = false
                                    adapter.setMatched(iS*cardLayout.columnCount+jS, true)
                                    adapter.setMatched(i*cardLayout.columnCount+j, true)
                                    Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()
                                    if (checkWin()){
                                        Settings.getData(requireContext()).saveLevel(level)
                                        object : CountDownTimer(2000, 1000) {
                                            override fun onTick(p0: Long) {
                                            }

                                            override fun onFinish() {
                                                parentFragmentManager.beginTransaction()
                                                    .setReorderingAllowed(true)
                                                    .add(R.id.container, FragmentVictory::class.java, bundleOf("level" to level))
                                                    .commit()
                                            }

                                        }.start()
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadData() {
        level = arguments?.getInt("level")!!
        cardLayout = binding.cardsLinerLayout
        images.add(Card(R.drawable.lion))
        images.add(Card(R.drawable.bird))
        images.add(Card(R.drawable.crocodile))
        images.add(Card(R.drawable.deer))
        images.add(Card(R.drawable.elephant))
        images.add(Card(R.drawable.fox))
        images.add(Card(R.drawable.frog))
        images.add(Card(R.drawable.giraffe))
        images.add(Card(R.drawable.hen))
        images.add(Card(R.drawable.horse))
        images.add(Card(R.drawable.mouse))
        images.add(Card(R.drawable.owl))
        images.add(Card(R.drawable.tiger))
        images.add(Card(R.drawable.raccoon))
        images.add(Card(R.drawable.shark))
        images.add(Card(R.drawable.snake))
        Collections.shuffle(images)

        when (level) {
            1 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 2
            }

            2 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 3
            }

            3 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 4
            }

            4 -> {
                cardLayout.rowCount = 3
                cardLayout.columnCount = 3
            }

            5 -> {
                cardLayout.rowCount = 3
                cardLayout.columnCount = 4
            }

            6 -> {
                cardLayout.rowCount = 4
                cardLayout.columnCount = 8
            }
        }
        var amount: Int = cardLayout.rowCount * cardLayout.columnCount / 2

        images.subList(amount, images.size).clear()
        images.addAll(images)
        Collections.shuffle(images)

        for (i in 0 until cardLayout.rowCount) {
            for (j in 0 until cardLayout.columnCount) {
                val index = i * cardLayout.columnCount + j
                val view = adapter.getView(index, null, cardLayout)
                cardLayout.addView(view)
            }
        }


    }

    fun flip_in(cardView: FrameLayout, imageCheck: ImageView) {
        val flipAnimatorThis =
            ObjectAnimator.ofFloat(cardView, "rotationY", 270f, 360f)
                .apply {
                    duration = 800
                    doOnEnd { cardView.isClickable = true }
                }
        val flipAnimatorBegin = ObjectAnimator.ofFloat(
            cardView,
            "rotationY",
            180f,
            270f
        ).apply {
            duration = 800
            doOnEnd {
                flipAnimatorThis.start()
                imageCheck.setImageResource(R.drawable.landscape_icon)
            }
        }
        flipAnimatorBegin.start()
    }

    fun flip_out(cardView: FrameLayout, image: ImageView, i: Int, j: Int) {
        val flipAnimator2 = ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
            duration = 800
        }
        val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
            duration = 800
            doOnEnd {
                flipAnimator2.start()
                image.setImageResource(images[i * cardLayout.columnCount + j].imageResId)
            }
        }
        flipAnimator.start()
    }
    fun checkWin(): Boolean{
        for (i in 0 until cardLayout.rowCount) {
            for (j in 0 until cardLayout.columnCount) {
                val cardView = cardLayout.getChildAt(i * cardLayout.columnCount + j) as FrameLayout
                if (adapter.getItem(i*cardLayout.columnCount+j)?.matched != true){
                    return false
                }
            }
        }
        return true
    }
}