package com.example.searchlunch.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchlunch.data.local.FavoriteRestaurantRepository
import com.example.searchlunch.data.local.QuickSearchRepository
import com.example.searchlunch.model.FavoriteRestaurant
import com.example.searchlunch.model.QuickSearch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    quickSearchRepository: QuickSearchRepository,
    favoriteRestaurantRepository: FavoriteRestaurantRepository,
) : ViewModel() {

    val quickSearchListStream: StateFlow<List<QuickSearch>> =
        quickSearchRepository.getAllQuickSearchSortedByUseCountStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    val favoriteRestaurantListStream: StateFlow<List<FavoriteRestaurant>> =
        favoriteRestaurantRepository.getAllFavoriteRestaurantsSortedByUpdatedAtStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )
}