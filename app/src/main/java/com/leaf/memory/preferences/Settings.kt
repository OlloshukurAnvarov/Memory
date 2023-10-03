package com.leaf.memory.preferences

import android.content.Context

class Settings(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    fun saveLevel(level: Int) = preferences.edit().putInt("level-index", level).apply()
    fun level() = preferences.getInt("level-index", 4)

    companion object {
        private lateinit var settings: Settings
        fun getData(context: Context): Settings {
            if (!::settings.isInitialized)
                settings = Settings(context)
            return settings
        }
    }

}