package com.example.moviez.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviez.data.models.User


@Database(entities = [User::class,MoviesDao::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): Daos
    abstract fun movieDao(): MoviesDao
}