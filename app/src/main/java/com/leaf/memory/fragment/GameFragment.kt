package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.databinding.FragmentGameBinding
import java.util.Collections

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private var level = 0
    private val images: ArrayList<Drawable> = ArrayList()
    private lateinit var card_layout: LinearLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
        loadViews()
        loadDataToViews()
    }

    private fun loadDataToViews() {

    }

    private fun loadViews() {
        for (i in 0 until card_layout.childCount){
            val layout = card_layout.getChildAt(i) as LinearLayout
            for(j in 0 until layout.childCount){
                val cardView = layout.getChildAt(j) as CardView
                val image = cardView.getChildAt(0) as ImageView
                cardView.setOnClickListener {
                    cardView.isClickable = false
                    val flipAnimator4 = ObjectAnimator.ofFloat(cardView, "rotationY", 270f, 360f).apply {
                        duration = 800
                        doOnEnd { cardView.isClickable = true }
                    }
                    val flipAnimator3 = ObjectAnimator.ofFloat(cardView, "rotationY", 180f, 270f).apply {
                        duration = 800
                        doOnEnd {
                            flipAnimator4.start()
                            image.setImageResource(R.drawable.landscape_icon)
                        }
                    }
                    val flipAnimator2 =  ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
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
                    val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
                        duration = 800
                        doOnEnd {
                            flipAnimator2.start()
                            image.setImageDrawable(images[(i+1)*4+j])
                        }
                    }
                    flipAnimator.start()

                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadData() {
        card_layout = binding.cardsLinerLayout
        images.add(resources.getDrawable(R.drawable.lion, null))
        images.add(resources.getDrawable(R.drawable.bird, null))
        images.add(resources.getDrawable(R.drawable.crocodile, null))
        images.add(resources.getDrawable(R.drawable.deer, null))
        images.add(resources.getDrawable(R.drawable.elephant, null))
        images.add(resources.getDrawable(R.drawable.fox, null))
        images.add(resources.getDrawable(R.drawable.frog, null))
        images.add(resources.getDrawable(R.drawable.giraffe, null))
        images.add(resources.getDrawable(R.drawable.hen, null))
        images.add(resources.getDrawable(R.drawable.horse, null))
        images.add(resources.getDrawable(R.drawable.mouse, null))
        images.add(resources.getDrawable(R.drawable.owl, null))
        images.add(resources.getDrawable(R.drawable.tiger, null))
        images.add(resources.getDrawable(R.drawable.raccoon, null))
        images.add(resources.getDrawable(R.drawable.shark, null))
        images.add(resources.getDrawable(R.drawable.snake, null))
        images.addAll(images)
        Collections.shuffle(images)


    }

    companion object{
        const val EASY_MODE = 1
        const val MEDIUM_MODE = 2
        const val HARD_MODE = 3
    }
}