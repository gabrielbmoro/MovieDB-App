package com.gabrielbmoro.programmingchallenge.repository.dataBase

import androidx.paging.DataSource
import androidx.room.*
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

@Dao
interface FavoriteMoviesDAO {

    @Query("SELECT * FROM  favorite_movies")
    fun allFavoriteMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM favorite_movies WHERE title = :title and originalTitle = :originalTitle")
    suspend fun isThereAMovie(title: String, originalTitle: String): List<Movie>

    @Query("DELETE FROM favorite_movies WHERE id  = :movieId")
    suspend fun removeFavorite(movieId: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(movie: Movie)

}