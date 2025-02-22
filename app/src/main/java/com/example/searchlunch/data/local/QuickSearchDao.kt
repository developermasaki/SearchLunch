package com.example.searchlunch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.searchlunch.model.QuickSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickSearchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quickSearch: QuickSearch)

    @Update
    suspend fun update(quickSearch: QuickSearch)

    @Delete
    suspend fun delete(quickSearch: QuickSearch)

    @Query("SELECT * from quickSearch ORDER BY useCount DESC")
    fun getAllQuickSearchSortedByUseCount(): Flow<List<QuickSearch>>
}