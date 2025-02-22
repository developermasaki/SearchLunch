package com.example.searchlunch

import android.app.Application
import com.example.searchlunch.data.remote.RestaurantListAppContainer
import com.example.searchlunch.data.remote.SearchAppContainer

class SearchLunchApplication: Application() {
    lateinit var searchAppContainer: SearchAppContainer

    override fun onCreate() {
        super.onCreate()
        searchAppContainer = RestaurantListAppContainer()
    }
}