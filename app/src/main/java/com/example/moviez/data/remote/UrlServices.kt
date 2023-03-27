package com.example.moviez.data.remote

import com.example.moviez.data.models.ErrorsModel
import com.example.moviez.data.models.MoviesModel
import com.example.moviez.data.remote.networkadapter.NetworkResponse
import retrofit2.http.*


interface UrlServices {
        @GET("movie/popular?language=en-US&region=US")
    suspend fun getMovies(
        @Query("page") page: Long
    ): NetworkResponse<MoviesModel, ErrorsModel>
//
//
//
//    @GET("/3/search/movie")
//    suspend fun searchMovies(
//        @Query("query") query: String,
//        @Query("page") page: String
//    ): Observable<SearchModel>
//
}