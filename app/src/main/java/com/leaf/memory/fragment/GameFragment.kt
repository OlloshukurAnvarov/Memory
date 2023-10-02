package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
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
                cardView.tag = false
                val image = cardView.getChildAt(0) as ImageView
                image.tag = images[i*7+j]

                cardView.setOnClickListener {
                    cardView.isClickable = false
                    cardView.tag = true
                    flip_out(cardView, image, i, j)

                    for (iS in 0 until card_layout.childCount) {
                        val layout = card_layout.getChildAt(iS) as LinearLayout
                        for (jS in 0 until layout.childCount) {
                            val cardViewCheck = layout.getChildAt(jS) as CardView
                            val imageCheck = cardViewCheck.getChildAt(0) as ImageView
                            if (i == iS && j == jS) {continue}
                            else  {
                                 if (cardViewCheck.tag == true && image.tag != imageCheck.tag){
                                     cardViewCheck.tag = false
                                     cardView.tag = false
                                    object : CountDownTimer(2000, 1000){
                                        override fun onTick(p0: Long) {

                                        }

                                        override fun onFinish() {
                                            Toast.makeText(requireContext(), "False", Toast.LENGTH_SHORT).show()

                                            flip_in(cardViewCheck, imageCheck)
                                            flip_in(cardView, image)
                                        }

                                    }.start()

                                }
                                else if (cardViewCheck.tag == true && image.tag == imageCheck.tag){
                                     cardViewCheck.tag = false
                                     cardView.tag = false
                                     Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()

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
    fun flip_in(cardView: CardView, imageCheck: ImageView){
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
    fun flip_out(cardView: CardView, image: ImageView, i:Int, j:Int){
        val flipAnimator2 =  ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
            duration = 800
        }
        val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
            duration = 800
            doOnEnd {
                flipAnimator2.start()
                image.setImageDrawable(images[i*7+j])
            }
        }
        flipAnimator.start()
    }
}