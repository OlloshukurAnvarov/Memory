package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentGameBinding

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private var mode = 0
    private lateinit var card_layout: LinearLayout
    init {
        loadData()
        loadViews()
        loadDataToViews()
    }

    private fun loadDataToViews() {

    }

    private fun loadViews() {
        var flipAnimator = ObjectAnimator()
        var flipAnimator2 = ObjectAnimator()
        var flipAnimator3 = ObjectAnimator()
        var flipAnimator4 = ObjectAnimator()
        for (i in 0..card_layout.childCount){
            val layout = card_layout.getChildAt(i) as LinearLayout
            for(j in 0..layout.childCount){
                val cardView = layout.getChildAt(j) as CardView
                val image = cardView.getChildAt(0) as ImageView
                cardView.setOnClickListener {
                    flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 180f).apply {
                        duration = 800
                        doOnEnd {
                            flipAnimator2.start()
                            cardView.foreground = resources.getDrawable(R.drawable.ic_launcher_foreground,null)
                        }
                    }
                    flipAnimator2 =  ObjectAnimator.ofFloat(cardView, "rotationY", 180f, 360f).apply {
                        duration = 800
                        doOnEnd {
                            object : CountDownTimer(3000, 1000){
                                override fun onTick(p0: Long) {

                                }

                                override fun onFinish() {
                                    flipAnimator3.start()
                                }

                            }.start()
                        }
                    }
                    flipAnimator3 = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 180f).apply {
                        duration = 800
                        doOnEnd {
                            flipAnimator4.start()
                            cardView.foreground = resources.getDrawable(R.drawable.clickable_effect)
                        }
                    }
                    flipAnimator4 = ObjectAnimator.ofFloat(cardView, "rotationY", 180f, 360f).apply {
                        duration = 800
                    }
                    flipAnimator.start()

                }
            }
        }
    }

    private fun loadData() {
        card_layout = binding.cardsLinerLayout
        mode = arguments?.getInt("mode")!!
    }

    companion object{
        const val EASY_MODE = 1
        const val MEDIUM_MODE = 2
        const val HARD_MODE = 3
    }
}