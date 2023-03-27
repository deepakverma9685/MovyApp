package com.example.moviez.data.local

import androidx.room.*
import com.example.moviez.data.models.MoviesItem
import com.example.moviez.data.models.User

@Dao
interface Daos {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesItem>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MoviesItem): Long

    @Query("SELECT * FROM MoviesItem WHERE title LIKE '%' || :search || '%'")
    fun loadHamsters(search: String?): List<MoviesItem>

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateMovie(movie: ResultsItem): Int
//
//    @Query("SELECT * FROM `ResultsItem` where id = :id")
//    fun getMovieById(id: Long?): ResultsItem
//
//    @Query("SELECT * FROM `ResultsItem` where id = :id")
//    fun getMovieDetailById(id: Long?): Flowable<ResultsItem>

//    @Query("SELECT * FROM `SearchResultsItem` where page = :page")
//    fun getMoviesByPage(page: Long): List<SearchResultsItem>

    @Query("SELECT * FROM `MoviesItem`")
    fun getMovies(): List<MoviesItem>
}