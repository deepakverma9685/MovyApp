package com.example.moviez.di


import com.example.moviez.data.local.AppDatabase
import com.example.moviez.data.remote.UrlServices
import com.example.moviez.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepository(urlServices: UrlServices,appDatabase: AppDatabase) = DataRepository(urlServices, appDatabase)
}