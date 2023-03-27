package com.example.moviez.repository

import com.example.moviez.data.local.AppDatabase
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.data.models.MoviesModel
import com.example.moviez.data.remote.Resource
import com.example.moviez.data.remote.UrlServices
import com.example.moviez.data.remote.networkadapter.NetworkResponse
import javax.inject.Inject
import com.example.moviez.data.remote.Result

class DataRepository @Inject constructor(
    private val urlServices: UrlServices,
    val appDatabase: AppDatabase
) {

    suspend fun getData(page: Long): Result<List<MoviesItem>> {
        Result.Loading(true)
        return when (val response = urlServices.getMovies(1)) {
            is NetworkResponse.Success -> {
                val movietiem = ArrayList<MoviesItem>()
                for (searchitem in response.body.results!!) {
                    searchitem?.page = response.body.page!!
                    searchitem?.totalPages = response.body.totalPages!!
                    if (searchitem != null) {
                        movietiem.add(searchitem)
                    }
                }
                appDatabase.movieDao().insertMovies(movietiem)
                Result.Success(appDatabase.movieDao().getMovies())
            }
            is NetworkResponse.ServerError -> {
                Result.Error(Exception(response.errorString),response.errorString)
            }
            else -> {
                Result.Error(Exception("response.errorString"))
            }
        }
    }

    suspend fun getMovies(): Resource<MoviesModel> {
        return when (val response = urlServices.getMovies(1)) {
            is NetworkResponse.Success -> {
                Resource.success(response.body,)
            }
            is NetworkResponse.ServerError -> {
                Resource.error(response.errorString, null)
            }
            else -> {
                Resource.error("", null)
            }
        }
    }

}