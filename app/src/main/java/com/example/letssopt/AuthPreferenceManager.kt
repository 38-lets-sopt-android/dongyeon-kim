package com.example.letssopt

import android.content.Context

object AuthPreferenceManager {
    private const val PREF_NAME = "auth_pref"
    private const val KEY_REGISTERED_EMAIL = "registered_email"
    private const val KEY_REGISTERED_PASSWORD = "registered_password"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveRegisteredAccount(context: Context, email: String, password: String) {
        prefs(context).edit()
            .putString(KEY_REGISTERED_EMAIL, email)
            .putString(KEY_REGISTERED_PASSWORD, password)
            .apply()
    }

    fun getRegisteredEmail(context: Context): String {
        return prefs(context).getString(KEY_REGISTERED_EMAIL, "") ?: ""
    }

    fun getRegisteredPassword(context: Context): String {
        return prefs(context).getString(KEY_REGISTERED_PASSWORD, "") ?: ""
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        prefs(context).edit()
            .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }
}
