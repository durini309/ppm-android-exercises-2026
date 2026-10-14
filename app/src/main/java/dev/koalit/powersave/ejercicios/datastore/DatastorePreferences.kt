package dev.koalit.powersave.ejercicios.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class DatastorePreferences(private val context: Context) {

    // 1. Definir las llaves
    private val emailKey = stringPreferencesKey("emailKey")
    private val isLoggedKey = booleanPreferencesKey("isLoggedKey")

    fun isLoggedIn(): Flow<Boolean> {
        return context
            .dataStore
            .data
            .map { preferences ->
                preferences[isLoggedKey] ?: false
            }
    }

    suspend fun getEmail(): String {
        val preferences = context.dataStore.data.first()
        return preferences[emailKey] ?: ""
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[isLoggedKey] = true
            preferences[emailKey] = email
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences[isLoggedKey] = false
            preferences[emailKey] = ""
        }
    }
}