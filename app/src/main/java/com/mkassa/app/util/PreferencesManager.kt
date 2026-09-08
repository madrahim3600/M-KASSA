package com.mkassa.app.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mkassa_settings")

class PreferencesManager(private val context: Context) {
    companion object {
        private val CURRENT_USER_ID = stringPreferencesKey("current_user_id")
        private val CURRENT_USER_ROLE = stringPreferencesKey("current_user_role")
        private val CURRENT_USERNAME = stringPreferencesKey("current_username")
        private val IS_LOGGED_IN = stringPreferencesKey("is_logged_in")
        private val THEME = stringPreferencesKey("theme")
        private val LANGUAGE = stringPreferencesKey("language")
    }

    val currentUserId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_USER_ID]
    }

    val currentUserRole: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_USER_ROLE]
    }

    val currentUsername: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_USERNAME]
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN]?.toBoolean() ?: false
    }

    val theme: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME] ?: "LIGHT"
    }

    val language: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE] ?: "uz"
    }

    suspend fun setCurrentUser(userId: String, role: String, username: String) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_USER_ID] = userId
            preferences[CURRENT_USER_ROLE] = role
            preferences[CURRENT_USERNAME] = username
            preferences[IS_LOGGED_IN] = "true"
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun setTheme(themeMode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = themeMode
        }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = lang
        }
    }
}
