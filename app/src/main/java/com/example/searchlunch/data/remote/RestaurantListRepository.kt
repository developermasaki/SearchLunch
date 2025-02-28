package com.example.searchlunch.data.remote

import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.model.Results
import com.example.searchlunch.network.RestaurantListApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

interface RestaurantListRepository {
    suspend fun getRestaurantList(
        refresh: Boolean,
        addSearch: Boolean,
        keyword: String = "",
        lat: Double = 0.0,
        lng: Double = 0.0,
        range: Int = 1,
        start: Int = 1
    ): RestaurantList
}

class NetworkRestaurantListRepository(
    private val restaurantListApiService: RestaurantListApiService
): RestaurantListRepository {
    private val latestRestaurantMutex = Mutex()

    private var latestRestaurant: RestaurantList = RestaurantList(Results())

    override suspend fun getRestaurantList(refresh: Boolean, addSearch: Boolean, keyword: String, lat: Double, lng: Double, range: Int, start: Int): RestaurantList = withContext(Dispatchers.IO){
        if(refresh){
            val networkResult = restaurantListApiService.getRestaurantList(
                keyword = keyword,
                lat = lat,
                lng = lng,
                range = range,
                start = start
            )
            if(addSearch){
                latestRestaurantMutex.withLock {
                    latestRestaurant = RestaurantList(
                        results = Results(
                            apiVersion = latestRestaurant.results.apiVersion,
                            resultsAvailable = latestRestaurant.results.resultsAvailable,
                            resultsReturned = latestRestaurant.results.resultsReturned,
                            shop = latestRestaurant.results.shop + networkResult.results.shop
                        )
                    )
                }
            } else {
                latestRestaurantMutex.withLock {
                    latestRestaurant = networkResult
                }
            }
        }
        latestRestaurantMutex.withLock {latestRestaurant}
    }
}