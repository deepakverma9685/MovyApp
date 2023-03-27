package com.example.moviez.di

import android.app.Application
import androidx.room.Room
import com.example.moviez.utils.AppConstants
import com.example.moviez.data.local.AppDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }
}