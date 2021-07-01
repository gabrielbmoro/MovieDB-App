package com.gabrielbmoro.programmingchallenge.repository.room

import androidx.room.*
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Dao
interface FavoriteMoviesDAO {

    @Query("SELECT * FROM  favorite_movies")
    suspend fun allFavoriteMovies(): List<Movie>

    @Query("SELECT * FROM favorite_movies WHERE title = :title and originalTitle = :originalTitle")
    suspend fun isThereAMovie(title: String, originalTitle: String): List<Movie>

    @Query("DELETE FROM favorite_movies WHERE id  = :movieId")
    suspend fun removeFavorite(movieId: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(movie: Movie)

}