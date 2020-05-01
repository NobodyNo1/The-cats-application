package com.bekarys.tech_assignment.thecats.features.common.permission

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PermissionSessionManager(context: Context) {

    private val MY_PREF = "permission_preferences"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

    fun firstTimeAsking(permission: String, isFirstTime: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(permission, isFirstTime)
        }.apply()
    }

    fun isFirstTimeAsking(permission: String): Boolean {
        return sharedPreferences.getBoolean(permission, true)
    }
}