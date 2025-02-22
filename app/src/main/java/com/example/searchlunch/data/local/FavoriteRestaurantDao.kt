package com.example.searchlunch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.searchlunch.model.FavoriteRestaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteRestaurant: FavoriteRestaurant)

    @Update
    suspend fun update(favoriteRestaurant: FavoriteRestaurant)

    @Delete
    suspend fun delete(favoriteRestaurant: FavoriteRestaurant)

    @Query("SELECT * from favoriteRestaurant WHERE id = :id")
    fun getFavoriteRestaurant(id: String): Flow<FavoriteRestaurant?>

    @Query("SELECT * from favoriteRestaurant ORDER BY updatedAt DESC")
    fun getAllFavoriteRestaurantsSortedByUpdatedAt(): Flow<List<FavoriteRestaurant>>
}