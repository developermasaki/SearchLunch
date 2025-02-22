package com.example.searchlunch

import android.app.Application
import com.example.searchlunch.data.local.FavoriteRestaurantAppContainer
import com.example.searchlunch.data.local.OfflineFavoriteRestaurantAppContainer
import com.example.searchlunch.data.local.OfflineQuickSearchAppContainer
import com.example.searchlunch.data.local.QuickSearchAppContainer
import com.example.searchlunch.data.remote.RestaurantListAppContainer
import com.example.searchlunch.data.remote.SearchAppContainer

class SearchLunchApplication: Application() {
    lateinit var searchAppContainer: SearchAppContainer
    lateinit var favoriteRestaurantAppContainer: FavoriteRestaurantAppContainer
    lateinit var quickSearchAppContainer: QuickSearchAppContainer

    override fun onCreate() {
        super.onCreate()
        searchAppContainer = RestaurantListAppContainer()
        favoriteRestaurantAppContainer = OfflineFavoriteRestaurantAppContainer(this)
        quickSearchAppContainer = OfflineQuickSearchAppContainer(this)
    }
}