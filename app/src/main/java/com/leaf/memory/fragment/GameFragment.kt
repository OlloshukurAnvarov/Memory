package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.MakeCards
import com.leaf.memory.R
import com.leaf.memory.VictoryDialog
import com.leaf.memory.adapter.CardAdapter
import com.leaf.memory.databinding.FragmentGameBinding
import com.leaf.memory.extensions.adapter
import com.leaf.memory.preferences.Settings

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private val settings by lazy { Settings(requireContext()) }
    private val images by lazy { MakeCards().cards(settings.level()) }
    private val adapter by lazy { CardAdapter(requireContext(), images) }
    private val cardsGrid: GridLayout by lazy { binding.cardsGrid }
    private var step = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadViews()
        loadData()
        loadDataToViews()
    }

    private fun loadViews() {
        val level = settings.level()
        cardsGrid.apply {
            if (level / 4 >= 5) {
                columnCount = level / 4
                rowCount = 4
            } else {
                columnCount = level / 2
                rowCount = 2
            }
        }
        cardsGrid.adapter(adapter)
    }

    private fun loadData() {
        for (i in 0 until cardsGrid.rowCount) {
            for (j in 0 until cardsGrid.columnCount) {
                val cardView = cardsGrid.getChildAt(i * cardsGrid.columnCount + j) as FrameLayout
                cardView.tag = false
                val image = cardView.getChildAt(0) as ImageView
                image.tag = images[i * cardsGrid.columnCount + j]

                cardView.setOnClickListener {
                    cardView.isClickable = false
                    cardView.tag = true
                    flipOut(cardView, image, i, j)

                    for (iS in 0 until cardsGrid.rowCount) {
                        for (jS in 0 until cardsGrid.columnCount) {
                            val cardViewCheck =
                                cardsGrid.getChildAt(iS * cardsGrid.columnCount + jS) as FrameLayout
                            val imageCheck = cardViewCheck.getChildAt(0) as ImageView
                            if (i == iS && j == jS) {
                                continue
                            } else {
                                if (cardViewCheck.tag == true && image.tag != imageCheck.tag) {
                                    cardViewCheck.tag = false
                                    cardView.tag = false
                                    setClickable(false)
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(p0: Long) {

                                        }

                                        override fun onFinish() {
                                            step++
                                            binding.step.text = step.toString()

                                            flipIn(cardViewCheck, imageCheck)
                                            flipIn(cardView, image)
                                        }

                                    }.start()

                                } else if (cardViewCheck.tag == true && image.tag == imageCheck.tag) {
                                    cardViewCheck.tag = false
                                    cardView.tag = false
                                    adapter.setMatched(iS * cardsGrid.columnCount + jS, true)
                                    adapter.setMatched(i * cardsGrid.columnCount + j, true)
                                    if (checkWin()) {
                                        object : CountDownTimer(500, 500) {
                                            override fun onTick(p0: Long) {
                                            }

                                            override fun onFinish() {
                                                VictoryDialog(requireContext()) { i ->
                                                    parentFragmentManager.beginTransaction()
                                                        .setReorderingAllowed(true)
                                                        .replace(
                                                            R.id.container,
                                                            GameFragment()
                                                        )
                                                        .commit()
                                                }
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

    private fun loadDataToViews() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun flipIn(cardView: FrameLayout, imageCheck: ImageView) {
        val flipAnimatorThis =
            ObjectAnimator.ofFloat(cardView, "rotationY", 270f, 360f)
                .apply {
                    duration = 500
                    doOnEnd {
                        cardView.isClickable = true
                        setClickable(true)
                    }
                }
        val flipAnimatorBegin = ObjectAnimator.ofFloat(
            cardView,
            "rotationY",
            180f,
            270f
        ).apply {
            duration = 500
            doOnEnd {
                flipAnimatorThis.start()
                imageCheck.setImageResource(R.drawable.landscape_icon)
            }
        }
        flipAnimatorBegin.start()
    }

    private fun flipOut(cardView: FrameLayout, image: ImageView, i: Int, j: Int) {
        val flipAnimator2 = ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
            duration = 500
        }
        val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
            duration = 500
            doOnEnd {
                flipAnimator2.start()
                image.setImageResource(images[i * cardsGrid.columnCount + j].imageResId)
            }
        }
        flipAnimator.start()
    }

    private fun checkWin(): Boolean {
        for (i in 0 until cardsGrid.rowCount) {
            for (j in 0 until cardsGrid.columnCount) {
                if (adapter.getItem(i * cardsGrid.columnCount + j)?.matched != true) {
                    return false
                }
            }
        }
        return true
    }

    private fun setClickable(value: Boolean) {
        for (i in 0 until cardsGrid.childCount) {
            val childView: View = cardsGrid.getChildAt(i)
            childView.isClickable = value
        }
    }

}