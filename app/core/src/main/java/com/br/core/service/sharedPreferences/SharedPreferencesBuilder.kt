package com.br.core.service.sharedPreferences

import android.content.Context
import android.content.SharedPreferences


data class SharedPreferencesBuilder(val context: Context){

    companion object {
        const val SHARED_PREFERENCES_KEY = "@save_students"
    }

    class GetInstance(val context: Context)  {
        private val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_KEY ,Context.MODE_PRIVATE)
        private val editor: SharedPreferences.Editor = sharedPref.edit()

        fun putString(key: String, data: String) {
            editor.putString(key, data).commit()
        }

        fun putBoolean(key: String, data: Boolean) {
            editor.putBoolean(key, data).commit()
        }

        fun getString(key: String): String? {
            return sharedPref.getString(key, "")
        }

        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return sharedPref.getBoolean(key, defaultValue)
        }
    }
}