package com.example.searchlunch.data.remote

import com.example.searchlunch.network.RestaurantListApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface SearchAppContainer {
    val searchLunchRepository: RestaurantListRepository
}

class RestaurantListAppContainer: SearchAppContainer {
    private val baseUrl = "http://webservice.recruit.co.jp"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RestaurantListApiService by lazy {
        retrofit.create(RestaurantListApiService::class.java)
    }

    override val searchLunchRepository: RestaurantListRepository by lazy {
        NetworkRestaurantListRepository(retrofitService)
    }
}