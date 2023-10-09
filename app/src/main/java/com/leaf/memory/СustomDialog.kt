package com.leaf.memory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.leaf.memory.preferences.Settings

class Dialog(context: Context, @LayoutRes layout: Int) {
    private val settings by lazy { Settings.getData(context) }

    private val view: View? =
        LayoutInflater.from(context).inflate(layout, null, false)

    private val next: TextView? = view?.findViewById(R.id.next)
    private val retry: ImageView? = view?.findViewById(R.id.retry)

    private val ok: Button? = view?.findViewById(R.id.ok)
    private val cancel: Button? = view?.findViewById(R.id.cancel)

    private val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme_Landscape)
        .setCancelable(false)
        .create()


     fun nextClickListener(l: () -> Unit) {
        next?.setOnClickListener {
            settings.saveLevel(settings.level() + 4)
            l.invoke()
            dialog.cancel()
        }
    }

     fun retryClickListener(l: () -> Unit) {
        retry?.setOnClickListener {
            settings.saveLevel(settings.level() + 4)
            l.invoke()
            dialog.cancel()
        }
    }


     fun cancelClickListener(l: () -> Unit) {
        cancel?.setOnClickListener {
            l.invoke()
            dialog.cancel()
        }
    }


     fun okClickListener(l: () -> Unit) {
        ok?.setOnClickListener {
            l.invoke()
            dialog.cancel()
        }
    }


     fun show() {
        dialog.setView(view)
        dialog.show()
    }

}