package com.example.searchlunch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.searchlunch.data.local.FavoriteRestaurantAppContainer
import com.example.searchlunch.data.local.OfflineFavoriteRestaurantAppContainer
import com.example.searchlunch.data.local.OfflineQuickSearchAppContainer
import com.example.searchlunch.data.local.QuickSearchAppContainer
import com.example.searchlunch.data.local.UserPreferencesRepository
import com.example.searchlunch.data.remote.RestaurantListAppContainer
import com.example.searchlunch.data.remote.SearchAppContainer

private const val SEARCH_PREFERENCE_NAME = "search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_PREFERENCE_NAME
)

class SearchLunchApplication: Application() {
    lateinit var searchAppContainer: SearchAppContainer
    lateinit var favoriteRestaurantAppContainer: FavoriteRestaurantAppContainer
    lateinit var quickSearchAppContainer: QuickSearchAppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        searchAppContainer = RestaurantListAppContainer()
        favoriteRestaurantAppContainer = OfflineFavoriteRestaurantAppContainer(this)
        quickSearchAppContainer = OfflineQuickSearchAppContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore = this.dataStore)
    }
}