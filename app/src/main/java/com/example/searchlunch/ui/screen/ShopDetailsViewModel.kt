package com.example.searchlunch.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchlunch.data.remote.RestaurantListRepository
import com.example.searchlunch.model.RestaurantList
import kotlinx.coroutines.launch

class ShopDetailsViewModel(
    saveStateHandle: SavedStateHandle,
    private val restaurantListRepository: RestaurantListRepository
): ViewModel() {
    private val itemId: String = checkNotNull(saveStateHandle[ShopDetailsDestination.ITEM_ID_ARG])

    var shopDetailsUiState by mutableStateOf(ShopDetailsUiState())
        private set
    var resultRestaurantList by mutableStateOf(RestaurantList())
        private set

    init {
        viewModelScope.launch {
            resultRestaurantList = restaurantListRepository.getRestaurantList(false, addSearch = false)
            shopDetailsUiState = ShopDetailsUiState(
                id = itemId,
                name = resultRestaurantList.results.shop.first {it?.id == itemId}?.name ?:"",
                address = resultRestaurantList.results.shop.first{ it?.id == itemId}?.address ?: "",
                open = resultRestaurantList.results.shop.first{ it?.id == itemId}?.open ?: "",
                access = resultRestaurantList.results.shop.first{ it?.id == itemId}?.access ?: "",
                shopPhoto = resultRestaurantList.results.shop.first{ it?.id == itemId}?.photo?.pc?.l ?: "",
                shopUrl = resultRestaurantList.results.shop.first{ it?.id == itemId}?.urls?.pc ?: ""
            )
        }
    }
}

data class ShopDetailsUiState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val open: String = "",
    val access: String = "",
    val shopPhoto: String = "",
    val shopUrl: String = ""
)