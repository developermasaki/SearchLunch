package com.example.searchlunch.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val keyword_KEYWORD = stringPreferencesKey("keyword")
        val RANGE_KEYWORD = intPreferencesKey("range")
        val LAT_KEYWORD = doublePreferencesKey("lat")
        val LNG_KEYWORD = doublePreferencesKey("lng")
    }

    suspend fun updateKeyword(keyword: String) {
        dataStore.edit { settings ->
            settings[keyword_KEYWORD] = keyword
        }
    }

    suspend fun updateRange(range: Int) {
        dataStore.edit { settings ->
            settings[RANGE_KEYWORD] = range
        }
    }

    suspend fun updateLat(lat: Double) {
        dataStore.edit { settings ->
            settings[LAT_KEYWORD] = lat
        }
    }

    suspend fun updateLng(lng: Double) {
        dataStore.edit { settings ->
            settings[LNG_KEYWORD] = lng
        }
    }

    val keywordFlow: Flow<String> = dataStore.data
        .flowOn(Dispatchers.IO)
        .catch {
            if(it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            Log.d("keyword", preferences[keyword_KEYWORD].toString())
            preferences[keyword_KEYWORD].toString()
        }

    val rangeFlow: Flow<Int> = dataStore.data
        .flowOn(Dispatchers.IO)
        .map{ preferences ->
            preferences[RANGE_KEYWORD] ?: 0
        }

    val latFlow: Flow<Double> = dataStore.data
        .flowOn(Dispatchers.IO)
        .map { preferences ->
            preferences[LAT_KEYWORD] ?: 0.0
        }

    val lngFlow: Flow<Double> = dataStore.data
        .flowOn(Dispatchers.IO)
        .map { preferences ->
            preferences[LNG_KEYWORD] ?: 0.0
        }
}