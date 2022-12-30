package com.example.courseworkcompose.data

import android.content.Context
import android.media.session.MediaSession.Token
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreToken(private val context: Context) {
    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserToken")
        val tokenPref = stringPreferencesKey("token")
        val userIdPref = intPreferencesKey("userId")
    }

    // to get the token
    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[tokenPref] ?: ""
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenPref] = token
        }
    }

    val getUserId: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[userIdPref] ?: -1
        }

    suspend fun saveUserId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[userIdPref] = id
        }
    }
}