package com.example.searchlunch.data.local

import com.example.searchlunch.model.FavoriteRestaurant
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRestaurantRepository(private val favoriteRestaurantDao: FavoriteRestaurantDao): FavoriteRestaurantRepository {
    override fun getAllFavoriteRestaurantsSortedByUpdatedAtStream(): Flow<List<FavoriteRestaurant>> = favoriteRestaurantDao.getAllFavoriteRestaurantsSortedByUpdatedAt()

    override fun getFavoriteRestaurantStream(id: String): Flow<FavoriteRestaurant?> = favoriteRestaurantDao.getFavoriteRestaurant(id)

    override suspend fun insertFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant) = favoriteRestaurantDao.insert(favoriteRestaurant)

    override suspend fun updateFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant) = favoriteRestaurantDao.update(favoriteRestaurant)

    override suspend fun deleteFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant) = favoriteRestaurantDao.delete(favoriteRestaurant)
}