package com.example.searchlunch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quickSearch")
data class QuickSearch(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val keyword: String = "",
    val range: Int = 1,
    val useCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastUsedAt: Long = System.currentTimeMillis()
)