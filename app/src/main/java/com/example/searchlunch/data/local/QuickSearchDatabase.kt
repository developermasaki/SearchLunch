package com.example.searchlunch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.searchlunch.model.QuickSearch

@Database(entities = [QuickSearch::class], version = 1, exportSchema = true)
abstract class QuickSearchDatabase: RoomDatabase() {
    abstract fun quickSearchDao(): QuickSearchDao

    companion object {
        @Volatile
        private var Instance: QuickSearchDatabase? = null

        fun getDatabase(context: Context): QuickSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, QuickSearchDatabase::class.java, "quickSearch")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}