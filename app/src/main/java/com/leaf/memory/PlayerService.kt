package com.leaf.memory

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.leaf.memory.preferences.Settings

class PlayerService : Service() {
    private val settings by lazy { Settings.getData(applicationContext) }
    private val mediaPlayer by lazy {
        MediaPlayer.create(applicationContext, R.raw.fh_4_song).apply {
            seekTo(settings.position())
            isLooping = true
        }
    }
    override fun onCreate() {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        settings.savePosition(mediaPlayer.currentPosition)
        mediaPlayer.reset()
    }
    override fun onBind(intent: Intent?): IBinder {
        return Binder()
    }
}