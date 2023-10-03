package com.leaf.memory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.leaf.memory.R
import com.leaf.memory.model.Card

class CardAdapter(context: Context, val list: List<Card>) : ArrayAdapter<Card>(context, 0, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)

        val card = getItem(position) ?: Card()
//        view.findViewById<ImageView>(R.id.card_icon).setImageResource(card.imageResId)
        return view
    }
    fun setMatched(p0: Int, value: Boolean){
        list[p0].matched = value
    }
}