package com.example.courseworkcompose.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Storage(private val context: Context) {
    // to make sure there is only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserToken")
        val tokenPref = stringPreferencesKey("token")
        val userIdPref = intPreferencesKey("userId")
        val familyIdPref = intPreferencesKey("familyId")
        val familyNamePref = stringPreferencesKey("familyName")
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

    val getFamilyName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[familyNamePref] ?: ""
        }

    suspend fun saveFamilyName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[familyNamePref] = name
        }
    }

    val getFamilyId: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[familyIdPref] ?: -1
        }

    suspend fun saveFamilyId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[familyIdPref] = id
        }
    }
}