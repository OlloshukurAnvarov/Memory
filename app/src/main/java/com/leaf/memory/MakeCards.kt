package com.leaf.memory

import com.leaf.memory.model.Card
import kotlin.random.Random

class MakeCards {
    private val cards = ArrayList<Card>()
    init {
        cards.clear()
        cards.add(Card(R.drawable.lion))
        cards.add(Card(R.drawable.bird))
        cards.add(Card(R.drawable.crocodile))
        cards.add(Card(R.drawable.deer))
        cards.add(Card(R.drawable.elephant))
        cards.add(Card(R.drawable.fox))
        cards.add(Card(R.drawable.frog))
        cards.add(Card(R.drawable.giraffe))
        cards.add(Card(R.drawable.hen))
        cards.add(Card(R.drawable.horse))
        cards.add(Card(R.drawable.mouse))
        cards.add(Card(R.drawable.owl))
        cards.add(Card(R.drawable.tiger))
        cards.add(Card(R.drawable.raccoon))
        cards.add(Card(R.drawable.shark))
        cards.add(Card(R.drawable.snake))
    }

    fun cards(level: Int): List<Card> {
        val pCards = ArrayList<Card>(level)

        for (i in 1..level / 2) {
            val randCardIndex = Random.nextInt(cards.size)
            pCards.add(cards[randCardIndex])
            pCards.add(cards[randCardIndex])
        }
        return pCards.shuffled()
    }

}