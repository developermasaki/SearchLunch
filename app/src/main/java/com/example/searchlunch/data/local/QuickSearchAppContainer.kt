package com.example.searchlunch.data.local

import android.content.Context

interface QuickSearchAppContainer {
    val quickSearchRepository: QuickSearchRepository
}

class OfflineQuickSearchAppContainer(context: Context): QuickSearchAppContainer {
    override val quickSearchRepository: QuickSearchRepository by lazy {
        OfflineQuickSearchRepository(QuickSearchDatabase.getDatabase(context).quickSearchDao())
    }
}