package com.example.searchlunch.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchlunch.data.local.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO 画面を開いたとき前回の検索内容が記入欄に表示されるようにする
class SearchViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var searchSheetUiState by mutableStateOf(SearchSheetUiState())
        private set



    fun updateUserPreferencesRepository(editType: EditType, value: String) {
        when(editType) {
            EditType.KEYWORD -> {
                searchSheetUiState = searchSheetUiState.copy(keyword = value)
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        userPreferencesRepository.updateKeyword(value)
                    }
                }
                Log.d("keyword", searchSheetUiState.keyword)
            }
            EditType.RANGE -> {
                searchSheetUiState = searchSheetUiState.copy(range = value.toInt())
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        userPreferencesRepository.updateRange(value.toInt())
                    }
                }
                Log.d("range", "${searchSheetUiState.range}")
            }
        }
    }

    fun updateLatAndLng(lat: Double, lng: Double) {
        viewModelScope.launch {
            userPreferencesRepository.updateLat(lat)
            userPreferencesRepository.updateLng(lng)
        }
    }
}

data class SearchSheetUiState(
    val keyword: String = "",
    val range: Int = 1,
)