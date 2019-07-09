package com.example.instagramlike

import android.content.Context
import android.preference.PreferenceManager

class Utility {

    fun getVide(context: Context, id : String): String? {
        return  PreferenceManager.getDefaultSharedPreferences(context).getString(id, "")
    }

    fun saveVideo(path: String, id : String, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(id, path)
        editor.apply()
    }
}