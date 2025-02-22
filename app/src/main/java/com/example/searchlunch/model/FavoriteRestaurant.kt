package com.example.searchlunch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteRestaurant")
data class FavoriteRestaurant(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val shopPhoto: String = "",
    val address: String = "",
    val open: String = "",
    val close: String = "",
    val access: String = "",
    val shopUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastViewedAt: Long = System.currentTimeMillis()
)