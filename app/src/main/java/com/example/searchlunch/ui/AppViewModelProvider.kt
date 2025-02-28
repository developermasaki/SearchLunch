package com.example.searchlunch.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.searchlunch.SearchLunchApplication
import com.example.searchlunch.ui.screen.HomeViewModel
import com.example.searchlunch.ui.screen.SearchResultViewModel
import com.example.searchlunch.ui.screen.SearchViewModel
import com.example.searchlunch.ui.screen.ShopDetailsViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeViewModel(
                quickSearchRepository = application().quickSearchAppContainer.quickSearchRepository,
                favoriteRestaurantRepository = application().favoriteRestaurantAppContainer.favoriteRestaurantRepository,
            )
        }
        initializer {
            SearchResultViewModel(
                restaurantListRepository = application().searchAppContainer.searchLunchRepository,
                favoriteRestaurantRepository = application().favoriteRestaurantAppContainer.favoriteRestaurantRepository,
                userPreferencesRepository = application().userPreferencesRepository
            )
        }
        initializer {
            ShopDetailsViewModel(
                this.createSavedStateHandle(),
                restaurantListRepository = application().searchAppContainer.searchLunchRepository,
            )
        }
        initializer {
            SearchViewModel(
                userPreferencesRepository = application().userPreferencesRepository
            )
        }
    }
}

fun CreationExtras.application(): SearchLunchApplication = (this[APPLICATION_KEY] as SearchLunchApplication)