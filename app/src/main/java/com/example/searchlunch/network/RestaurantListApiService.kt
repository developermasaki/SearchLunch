package com.example.searchlunch.network

import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query


interface RestaurantListApiService {
    @GET("hotpepper/gourmet/v1")
    suspend fun getRestaurantList(
        @Query("key") key: String = BuildConfig.GOURMENT_SEARCH_API_KEY,
        @Query("keyword") keyword: String? = null,
        @Query("lat") lat: Double = 0.0,
        @Query("lng") lng: Double = 0.0,
        @Query("range") range: Int = 1,
        @Query("count") count: Int = 50,
        @Query("start") start: Int = 1,
        @Query("format") format: String = "json"
    ): RestaurantList
}