package com.leaf.memory.preferences

import android.content.Context

class Settings(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveLevel(level: Int) = preferences.edit().putInt("level", level).apply()
    fun level() = preferences.getInt("level", 4)

    fun saveBool(boolean: Boolean) = preferences.edit().putBoolean("play-index", boolean).apply()
    fun bool() = preferences.getBoolean("play-index", false)

    fun savePosition(position: Int) = preferences.edit().putInt("music-position", position).apply()
    fun position() = preferences.getInt("music-position", 0)

    companion object {
        private lateinit var settings: Settings
        fun getData(context: Context): Settings {
            if (!::settings.isInitialized)
                settings = Settings(context)
            return settings
        }
    }

}