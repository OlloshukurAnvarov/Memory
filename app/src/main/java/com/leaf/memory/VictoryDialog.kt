package com.leaf.memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class VictoryDialog (context: Context, level: Int, listener: OnNextClickListener){
    private val dialog = AlertDialog.Builder(context)

    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_victory, null, false)
        val next: TextView = view.findViewById(R.id.next)
        val retry: ImageView = view.findViewById(R.id.retry)

        dialog.setView(view)
        dialog.show()

        next.setOnClickListener {
            listener.onNextlick(level+1)
        }
        retry.setOnClickListener {
            listener.onNextlick(level)
        }
    }
}
fun interface OnNextClickListener {
    fun onNextlick(level: Int)
}