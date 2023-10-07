package com.leaf.memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.leaf.memory.preferences.Settings


class VictoryDialog(context: Context, listener: OnNextClickListener) {
    private val settings by lazy { Settings.getData(context) }
    private val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme_Landscape)
        .setCancelable(false)
        .create()

    init {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_victory, null, false)
        val next: TextView = view.findViewById(R.id.next)
        val retry: ImageView = view.findViewById(R.id.retry)
        val level = settings.level()

        next.setOnClickListener {
            listener.onNextClick(level + 4)
            Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
            settings.saveLevel(level + 2)
            cancel()
        }
        retry.setOnClickListener {
            listener.onNextClick(level)
            cancel()
            Settings.getData(context).saveLevel(level)
        }
        dialog.setView(view)
        dialog.show()
    }

    private fun cancel() {
        dialog.cancel()
    }
}

fun interface OnNextClickListener {
    fun `onNextClick`(level: Int)
}