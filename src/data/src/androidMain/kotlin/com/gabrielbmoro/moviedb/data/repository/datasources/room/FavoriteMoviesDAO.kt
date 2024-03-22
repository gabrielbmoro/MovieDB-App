package com.gabrielbmoro.moviedb.data.repository.datasources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrielbmoro.moviedb.data.repository.datasources.room.dto.FavoriteMovieDTO
import kotlinx.coroutines.flow.Flow

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
