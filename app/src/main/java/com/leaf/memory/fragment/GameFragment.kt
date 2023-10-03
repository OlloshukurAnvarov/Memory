package com.leaf.memory.fragment

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.leaf.memory.R
import com.leaf.memory.adapter.CardAdapter
import com.leaf.memory.databinding.FragmentGameBinding
import com.leaf.memory.model.Card
import java.util.Collections

class GameFragment : Fragment(R.layout.fragment_game) {
    private val binding: FragmentGameBinding by viewBinding()
    private val images = ArrayList<Card>()
    private val adapter by lazy { CardAdapter(requireContext(), images) }
    private var level = 0
    private lateinit var cardLayout: GridView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
        loadViews()
        loadDataToViews()
    }

    private fun loadDataToViews() {
        binding.cardsLinerLayout.adapter = adapter
    }

    private fun loadViews() {
        for (i in 0 until cardLayout.childCount){
            val layout = cardLayout.getChildAt(i) as LinearLayout
            for(j in 0 until layout.childCount){
                val cardView = layout.getChildAt(j) as FrameLayout
                cardView.tag = false
                val image = cardView.getChildAt(0) as ImageView
                image.tag = images[i*8+j]

                cardView.setOnClickListener {
                    cardView.isClickable = false
                    cardView.tag = true
                    flip_out(cardView, image, i, j)

                    for (iS in 0 until cardLayout.childCount) {
                        val layout = cardLayout.getChildAt(iS) as LinearLayout
                        for (jS in 0 until layout.childCount) {
                            val cardViewCheck = layout.getChildAt(jS) as FrameLayout
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
        images.addAll(images)
        Collections.shuffle(images)


    }
    fun flip_in(cardView: FrameLayout, imageCheck: ImageView){
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
    fun flip_out(cardView: FrameLayout, image: ImageView, i:Int, j:Int){
        val flipAnimator2 =  ObjectAnimator.ofFloat(cardView, "rotationY", 90f, 180f).apply {
            duration = 800
        }
        val flipAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", 0f, 90f).apply {
            duration = 800
            doOnEnd {
                flipAnimator2.start()
                image.setImageResource(images[i*8+j].imageResId)
            }
        }
        flipAnimator.start()
    }
}