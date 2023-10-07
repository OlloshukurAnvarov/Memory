package com.leaf.memory

import android.content.Context
import android.content.DialogInterface.OnShowListener
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.leaf.memory.extensions.dp
import com.leaf.memory.preferences.Settings


class VictoryDialog (context: Context, level: Int, listener: OnNextClickListener){
    private val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme_Landscape)
        .setCancelable(false)
        .create()

    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_victory, null, false)
        val next: TextView = view.findViewById(R.id.next)
        val retry: ImageView = view.findViewById(R.id.retry)

        next.setOnClickListener {
            listener.onNextlick(level+1)
            Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
            Settings.getData(context).saveLevel(level+1)
            cancel()
        }
        retry.setOnClickListener {
            listener.onNextlick(level)
            cancel()
            Settings.getData(context).saveLevel(level)
        }
        dialog.setView(view)
        dialog.show()
    }
    fun cancel(){
        dialog.cancel()
    }
}
fun interface OnNextClickListener {
    fun onNextlick(level: Int)
}