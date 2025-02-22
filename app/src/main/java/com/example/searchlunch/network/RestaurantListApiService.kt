package com.example.searchlunch.network

import com.example.searchlunch.model.RestaurantList
import com.example.searchlunch.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

enum class Range(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5)
}

interface RestaurantListApiService {
    @GET("hotpepper/gourmet/v1")
    suspend fun getRestaurantList(
        @Query("key") key: String = BuildConfig.GOURMENT_SEARCH_API_KEY,
        @Query("keyword") keyword: String? = null,
        @Query("lat") lat: Double = 0.0,
        @Query("lng") lng: Double = 0.0,
        @Query("range") range: Range = Range.ONE,
        @Query("type") type: String = "lite",
        @Query("start") start: Int = 1,
        @Query("count") count: Int = 50,
        @Query("format") format: String = "json"
    ): RestaurantList
}