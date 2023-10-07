package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.MakeCards
import com.leaf.memory.R
import com.leaf.memory.VictoryDialog
import com.leaf.memory.adapter.CardAdapter
import com.leaf.memory.databinding.FragmentGameBinding
import com.leaf.memory.model.Card
import com.leaf.memory.preferences.Settings

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private val images = ArrayList<Card>()
    private val adapter by lazy { CardAdapter(requireContext(), images) }
    private var level = 4
    private lateinit var cardLayout: GridLayout
    private lateinit var counter: TextView
    private var step = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
        loadViews()
        loadDataToViews()
    }

    private fun loadDataToViews() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun loadViews() {
        for (i in 0 until cardLayout.rowCount) {
            for (j in 0 until cardLayout.columnCount) {
                val cardView = cardLayout.getChildAt(i * cardLayout.columnCount + j) as FrameLayout
                cardView.tag = false
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
                                    setClickable(false)
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(p0: Long) {

                                        }

                                        override fun onFinish() {
                                            step++
                                            counter.setText(step.toString())

                                            flip_in(cardViewCheck, imageCheck)
                                            flip_in(cardView, image)
                                        }

                                    }.start()

                                } else if (cardViewCheck.tag == true && image.tag == imageCheck.tag) {
                                    cardViewCheck.tag = false
                                    cardView.tag = false
                                    adapter.setMatched(iS * cardLayout.columnCount + jS, true)
                                    adapter.setMatched(i * cardLayout.columnCount + j, true)
                                    if (checkWin()) {
                                        Settings.getData(requireContext()).saveLevel(level)
                                        object : CountDownTimer(500, 500) {
                                            override fun onTick(p0: Long) {
                                            }

                                            override fun onFinish() {
                                                VictoryDialog(requireContext(), level) { i ->
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadData() {
        level = Settings.getData(requireContext()).level()
        cardLayout = binding.cardsGrid
        val cards: MakeCards = MakeCards()
        images.addAll(cards.cards(level))
        counter = binding.step

        when (level) {
            4 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 2
            }

            6 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 3
            }

            8 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 4
            }

            10 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 5
            }

            12 -> {
                cardLayout.rowCount = 3
                cardLayout.columnCount = 4
            }

            14 -> {
                cardLayout.rowCount = 2
                cardLayout.columnCount = 7
            }
        }

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

    fun flip_out(cardView: FrameLayout, image: ImageView, i: Int, j: Int) {
        val flipAnimator2 = ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
            duration = 500
        }
        val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
            duration = 500
            doOnEnd {
                flipAnimator2.start()
                image.setImageResource(images[i * cardLayout.columnCount + j].imageResId)
            }
        }
        flipAnimator.start()
    }

    fun checkWin(): Boolean {
        for (i in 0 until cardLayout.rowCount) {
            for (j in 0 until cardLayout.columnCount) {
                if (adapter.getItem(i * cardLayout.columnCount + j)?.matched != true) {
                    return false
                }
            }
        }
        return true
    }
    fun setClickable(value: Boolean){
        for (i in 0 until cardLayout.getChildCount()) {
            val childView: View = cardLayout.getChildAt(i)
            childView.isClickable =value
        }
    }
}