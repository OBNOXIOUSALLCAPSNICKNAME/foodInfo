package com.example.foodinfo.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject


class PrefUtils @Inject constructor(
    context: Context
) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var searchFilter: String = ""
        get() = preferences.getString(SEARCH_FILTER, "") ?: ""
        set(value) {
            preferences.edit { putString(SEARCH_FILTER, value) }
            field = value
        }

    var apiCredentials: String = ""
        get() = preferences.getString(API_CREDENTIALS, "") ?: ""
        set(value) {
            preferences.edit { putString(API_CREDENTIALS, value) }
            field = value
        }

    companion object {
        private const val SEARCH_FILTER = "SEARCH_FILTER"
        private const val API_CREDENTIALS = "API_CREDENTIALS"
    }
}