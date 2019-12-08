package com.homapps.kotlin_firebase_samplesolutions.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "AppPreferences"
    private const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME,
            MODE)
    }
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userId: String?
        get() = preferences.getString("userId", "")
        set(value) = preferences.edit {
            it.putString("userId", value)
        }

}