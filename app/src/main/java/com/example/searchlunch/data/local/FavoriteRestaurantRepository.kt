package com.example.searchlunch.data.local

import com.example.searchlunch.model.FavoriteRestaurant
import kotlinx.coroutines.flow.Flow

interface FavoriteRestaurantRepository {
    // 全てのお気に入りレストランを最終更新日の降順で提供
    fun getAllFavoriteRestaurantsSortedByUpdatedAtStream(): Flow<List<FavoriteRestaurant>>

    // お気に入りのレストランを提供
    fun getFavoriteRestaurantStream(id: String): Flow<FavoriteRestaurant?>

    // お気に入りのレストランを登録
    suspend fun insertFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant)

    // お気に入りのレストランの情報を更新
    suspend fun deleteFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant)

    // お気に入りのレストランを削除
    suspend fun updateFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant)
}