package com.example.foodinfo.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject


class PrefUtils @Inject constructor(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var searchFilter: String = ""
        get() = preferences.getString(SEARCH_FILTER, "") ?: ""
        set(value) {
            preferences.edit { putString(SEARCH_FILTER, value) }
            field = value
        }

    var edamamCredentials: String = ""
        get() = preferences.getString(EDAMAM_CREDENTIALS, "") ?: ""
        set(value) {
            preferences.edit { putString(EDAMAM_CREDENTIALS, value) }
            field = value
        }

    var githubCredentials: String = ""
        get() = preferences.getString(GITHUB_CREDENTIALS, "") ?: ""
        set(value) {
            preferences.edit { putString(GITHUB_CREDENTIALS, value) }
            field = value
        }


    companion object {
        private const val SEARCH_FILTER = "SEARCH_FILTER"
        private const val EDAMAM_CREDENTIALS = "EDAMAM_CREDENTIALS"
        private const val GITHUB_CREDENTIALS = "GITHUB_CREDENTIALS"
    }
}