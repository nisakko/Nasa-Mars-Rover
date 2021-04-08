package com.example.nasamarsrover.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Transformations
import com.example.nasamarsrover.application.PREFERENCES_NAME
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class PreferenceRepository @Inject constructor(@ApplicationContext private val context: Context, val gson: Gson) {

    var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun remove(key: String): Boolean {
        return try {
            sharedPreferences.edit().remove(key).apply()
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun <T> saveObject(key: String, obj: T): Boolean {
        sharedPreferences.edit().apply {
            putString(key, gson.toJson(obj))
            return commit()
        }
    }

    inline fun <reified T> readObjectNow(key: String): T? {
        try {
            val json: String = sharedPreferences.getString(key, null) ?: return null
            return if (isValidJson(json)) {
                gson.fromJson<T>(json, object : TypeToken<T>() {}.type)
            } else {
                null
            }
        } catch (exp: JsonSyntaxException) {
            return null
        }
    }

    inline fun <reified T> readObject(key: String) =
        Transformations.map(sharedPreferences.stringLiveData(key, "")) {
            try {
                if (isValidJson(it)) {
                    gson.fromJson<T>(it, object : TypeToken<T>() {}.type)
                } else {
                    null
                }
            } catch (exp: JsonSyntaxException) {
                null
            }
        }

    fun isValidJson(json: String): Boolean {
        return json.isNotEmpty()
    }
}