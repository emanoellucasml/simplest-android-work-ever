package com.example.formcadastro

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    private var sharedPreferences: SharedPreferences

    init {
       sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)
    }

    fun storeString(key: String, value: String) {
        this.sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getStoredString(key: String): String {
        return this.sharedPreferences.getString(key, "") ?: ""
    }

    fun storeBoolean(key: String, value: Boolean){
        var string: String = "false"
        if(value){
            string = "true"
        }
        this.storeString(key, string)
    }

    fun getStoredBoolean(key: String) : Boolean{
        val string: String? = this.getStoredString(key)
        if(string != null && string.isNotEmpty() && string.isNotBlank()){
            return string.toBoolean()
        }
        return false
    }

}