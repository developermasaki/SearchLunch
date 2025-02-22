package com.example.searchlunch.data.local

import com.example.searchlunch.model.QuickSearch
import kotlinx.coroutines.flow.Flow

interface QuickSearchRepository {
    // 全てのクイック検索を使用頻度順に逆順で提供
    fun getAllQuickSearchSortedByUseCountStream(): Flow<List<QuickSearch>>

    // クイック検索を保存
    suspend fun insertQuickSearch(quickSearch: QuickSearch)

    // クイック検索を更新
    suspend fun updateQuickSearch(quickSearch: QuickSearch)

    // クイック検索を削除
    suspend fun deleteQuickSearch(quickSearch: QuickSearch)
}