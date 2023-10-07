package com.leaf.memory.extensions

import android.widget.ArrayAdapter
import android.widget.GridLayout
import com.leaf.memory.adapter.CardAdapter

fun GridLayout.adapter(adapter: CardAdapter) {
    val cardsGrid = this

    for (i in 0 until cardsGrid.rowCount) {
        for (j in 0 until cardsGrid.columnCount) {
            val index = i * cardsGrid.columnCount + j
            val view = adapter.getView(index, null, cardsGrid)
            cardsGrid.addView(view)
        }
    }

}