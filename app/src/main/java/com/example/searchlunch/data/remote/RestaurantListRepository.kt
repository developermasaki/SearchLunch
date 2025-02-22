package com.example.searchlunch.data.remote

import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.network.RestaurantListApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RestaurantListRepository {
    suspend fun getRestaurantList(): RestaurantList
}

class NetworkRestaurantListRepository(
    private val restaurantListApiService: RestaurantListApiService
): RestaurantListRepository {
    override suspend fun getRestaurantList(): RestaurantList = withContext(Dispatchers.IO){
        restaurantListApiService.getRestaurantList()
    }
}