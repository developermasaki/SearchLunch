package com.example.searchlunch.data.local

import android.content.Context

interface FavoriteRestaurantAppContainer {
    val favoriteRestaurantRepository: FavoriteRestaurantRepository
}

class OfflineFavoriteRestaurantAppContainer(context: Context): FavoriteRestaurantAppContainer {
    override val favoriteRestaurantRepository: FavoriteRestaurantRepository by lazy {
        OfflineFavoriteRestaurantRepository(FavoriteRestaurantDatabase.getDatabase(context).favoriteRestaurantDao())
    }
}