package com.example.searchlunch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.searchlunch.model.FavoriteRestaurant

@Database(entities = [FavoriteRestaurant::class], version = 1, exportSchema = true)
abstract class FavoriteRestaurantDatabase: RoomDatabase() {
    abstract fun favoriteRestaurantDao(): FavoriteRestaurantDao

    companion object{
        @Volatile
        private var Instance: FavoriteRestaurantDatabase? = null

        fun getDatabase(context: Context): FavoriteRestaurantDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavoriteRestaurantDatabase::class.java, "favoriteRestaurant")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}