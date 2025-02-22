package com.example.searchlunch.fake

import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.network.Range
import com.example.searchlunch.network.RestaurantListApiService

class FakeRestaurantListApiService: RestaurantListApiService {
    override suspend fun getRestaurantList(
        key: String,
        keyword: String?,
        lat: Double,
        lng: Double,
        range: Range,
        start: Int,
        count: Int,
        format: String
    ): RestaurantList {
        return FakeDataSource.searchRestaurantList
    }
}