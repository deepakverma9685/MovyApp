package com.example.moviez.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.data.models.User


@Database(entities = [User::class,MoviesItem::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): Daos
    abstract fun movieDao(): MoviesDao
}