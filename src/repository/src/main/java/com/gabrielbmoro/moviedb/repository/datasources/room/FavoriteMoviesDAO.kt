package com.gabrielbmoro.moviedb.repository.datasources.room

import androidx.room.*
import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO

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