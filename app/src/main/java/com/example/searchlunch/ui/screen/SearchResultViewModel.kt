package com.example.searchlunch.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchlunch.data.local.FavoriteRestaurantRepository
import com.example.searchlunch.data.local.UserPreferencesRepository
import com.example.searchlunch.data.remote.RestaurantListRepository
import com.example.searchlunch.model.FavoriteRestaurant
import com.example.searchlunch.model.Shop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

sealed interface SearchUiState {
    data class Success(val restaurantList: List<Shop?> = listOf()) : SearchUiState
    data object Error : SearchUiState
    data object Loading : SearchUiState
}

class SearchResultViewModel(
    private val restaurantListRepository: RestaurantListRepository,
    private val favoriteRestaurantRepository: FavoriteRestaurantRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Loading)
        private set
    val favoriteBookList: StateFlow<List<FavoriteRestaurant>> = favoriteRestaurantRepository
        .getAllFavoriteRestaurantsSortedByUpdatedAtStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = listOf()
        )
    private var _showedRestaurant = MutableStateFlow<List<Shop?>>(listOf())
    val showedRestaurant: StateFlow<List<Shop?>> = _showedRestaurant.asStateFlow()


    init {
        viewModelScope.launch {
            try {
                Log.d("searchResultViewModel", "init")
                val result = restaurantListRepository.getRestaurantList(
                    refresh = true,
                    addSearch = false,
                    keyword = userPreferencesRepository.keywordFlow.first(),
                    lat = userPreferencesRepository.latFlow.first(),
                    lng = userPreferencesRepository.lngFlow.first(),
                    range = userPreferencesRepository.rangeFlow.first(),
                    start = 1
                ).results.shop
                _showedRestaurant.update {
                    result
                }
                searchUiState = SearchUiState.Success()
                Log.d("searchResultViewModel", "keyword${userPreferencesRepository.keywordFlow.first()}, range${userPreferencesRepository.rangeFlow.first()}, lat${userPreferencesRepository.latFlow.first()}, lng${userPreferencesRepository.lngFlow.first()}, start${1}")
                Log.d("searchResultViewModel", "initFinish${searchUiState}${result}")
            } catch (e: IOException) {
                Log.d("SearchResultViewModel", "IOException${e}")
                searchUiState = SearchUiState.Error
            }

        }
    }

    fun fetchMoreResult(index: Int) {
        val start = 50 * index / 50 + 1
        viewModelScope.launch {
            try {
                val nowRestaurantList = restaurantListRepository.getRestaurantList(
                    refresh = false,
                    addSearch = true,
                    keyword = userPreferencesRepository.keywordFlow.first(),
                    lat = userPreferencesRepository.latFlow.first(),
                    lng = userPreferencesRepository.lngFlow.first(),
                    range = userPreferencesRepository.rangeFlow.first(),
                    start = start
                ).results.shop

                _showedRestaurant.update {
                    it + nowRestaurantList
                }
            } catch (e: IOException) {
                Log.d("SearchResultViewModel", "IOException${e}")
            }
        }
    }

    fun toggleFavoriteRestaurant(shop: Shop, isFavorite: Boolean) {
        viewModelScope.launch {
            if(isFavorite) {
                favoriteRestaurantRepository.insertFavoriteRestaurant(shop.toFavoriteRestaurant())
            } else {
                favoriteRestaurantRepository.deleteFavoriteRestaurant(shop.toFavoriteRestaurant())
            }
        }
    }
}

fun Shop.toFavoriteRestaurant(): FavoriteRestaurant = FavoriteRestaurant(
    id = id ?: UUID.randomUUID().toString(),
    name = name ?: "",
    shopPhoto = photo?.pc?.l ?: "",
    address = address ?: "",
    open = open ?: "",
    close = close ?: "",
    access = access ?: "",
    shopUrl = urls?.pc ?: "",
    createdAt = System.currentTimeMillis(),
    updatedAt = System.currentTimeMillis(),
    lastViewedAt = System.currentTimeMillis()
)

enum class EditType{
    KEYWORD, RANGE
}