package com.gabrielbmoro.programmingchallenge.repository.room

import androidx.room.*
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO

@Dao
interface FavoriteMoviesDAO {

    @Query("SELECT * FROM favorite_movies")
    suspend fun allFavoriteMovies(): List<FavoriteMovieDTO>

    @Query("SELECT * FROM favorite_movies WHERE title = :title")
    suspend fun isThereAMovie(title: String): List<FavoriteMovieDTO>

    @Query("DELETE FROM favorite_movies WHERE title  = :movieTitle")
    suspend fun removeFavorite(movieTitle: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(movieDTO: FavoriteMovieDTO)
}