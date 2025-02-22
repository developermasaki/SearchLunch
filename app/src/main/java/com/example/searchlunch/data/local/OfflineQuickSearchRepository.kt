package com.example.searchlunch.data.local

import com.example.searchlunch.model.QuickSearch
import kotlinx.coroutines.flow.Flow

class OfflineQuickSearchRepository(private val quickSearchDao: QuickSearchDao): QuickSearchRepository {
    override fun getAllQuickSearchSortedByUseCountStream(): Flow<List<QuickSearch>> = quickSearchDao.getAllQuickSearchSortedByUseCount()

    override suspend fun insertQuickSearch(quickSearch: QuickSearch) = quickSearchDao.insert(quickSearch)

    override suspend fun updateQuickSearch(quickSearch: QuickSearch) = quickSearchDao.update(quickSearch)

    override suspend fun deleteQuickSearch(quickSearch: QuickSearch) = quickSearchDao.delete(quickSearch)
}