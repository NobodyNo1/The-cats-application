package com.bekarys.tech_assignment.thecats.features.common.preferences

import android.content.Context

private const val API_KEY_PREFERENCE = "api_key"
private const val DEFAULT_API_KEY = ""

private const val API_BASE_URL_PREFERENCE = "api_base_url"
private const val DEFAULT_API_BASE_URL = ""

class AppPreferences(
    context: Context
) {

    private val preferences = context.getSharedPreferences(
        "AppPreferences", android.content.Context.MODE_PRIVATE
    )

    fun getApiKey(): String {
        return preferences.getString(API_KEY_PREFERENCE, DEFAULT_API_KEY) ?: DEFAULT_API_KEY
    }

    @Synchronized
    fun setApiKey(value: String) {
        preferences.edit()
            .putString(API_KEY_PREFERENCE, value)
            .apply()
    }

    fun getCatServiceBaseUrl(): String {
        return preferences.getString(API_BASE_URL_PREFERENCE, DEFAULT_API_BASE_URL) ?: DEFAULT_API_BASE_URL
    }

    @Synchronized
    fun setCatServiceBaseUrl(value: String) {
        preferences.edit()
            .putString(API_BASE_URL_PREFERENCE, value)
            .apply()
    }
}