package me.niccorder.weather.internal.infra

import android.content.Context
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.gson.Gson
import com.google.gson.JsonObject
import me.niccorder.weather.internal.AppScope
import java.util.*
import javax.inject.Inject

/**
 * One of our most basic infrastructure pieces. The KVStore!
 */
interface KVStore<in Key, in Value> {
    fun <T : Any> get(key: Key): T
    fun put(key: Key, value: Value)
}

@AppScope
class SharedPreferencesStore @Inject constructor(
        private val preferences: SharedPreferences,
        private val gson: Gson
) : KVStore<String, Any?> {

    override fun put(key: String, value: Any?) = preferences.edit()
            .putString(key, gson.toJson(value))
            .apply()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String): T = gson.fromJson(
            preferences.getString(key, null),
            T::class.java
    ) as T
}