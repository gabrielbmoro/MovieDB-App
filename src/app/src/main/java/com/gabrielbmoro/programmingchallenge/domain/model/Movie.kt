package com.gabrielbmoro.programmingchallenge.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * This data class represents the Movie.
 * The annotations used was the Retrofit and Gson library.
 * @author Gabriel Moro
 * @since 2018-08-30
 */
@Entity(tableName = "favorite_movies")
open class Movie(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        var id: Int? = null,
        val votes: Int,
        val isVideo: Boolean,
        val votesAverage: Float,
        val title: String,
        val popularity: Float,
        val posterPath: String,
        val originalLanguage: String,
        val originalTitle: String,
        val backdropPath: String,
        val isAdult: Boolean,
        val overview: String,
        val releaseDate: String,
        var isFavorite: Boolean = false
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readInt(),
                parcel.readByte() != 0.toByte(),
                parcel.readFloat(),
                parcel.readString() ?: "",
                parcel.readFloat(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readByte() != 0.toByte(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readByte() != 0.toByte()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeInt(votes)
                parcel.writeByte(if (isVideo) 1 else 0)
                parcel.writeFloat(votesAverage)
                parcel.writeString(title)
                parcel.writeFloat(popularity)
                parcel.writeString(posterPath)
                parcel.writeString(originalLanguage)
                parcel.writeString(originalTitle)
                parcel.writeString(backdropPath)
                parcel.writeByte(if (isAdult) 1 else 0)
                parcel.writeString(overview)
                parcel.writeString(releaseDate)
                parcel.writeByte(if (isFavorite) 1 else 0)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Movie> {
                override fun createFromParcel(parcel: Parcel): Movie {
                        return Movie(parcel)
                }

                override fun newArray(size: Int): Array<Movie?> {
                        return arrayOfNulls(size)
                }
        }
}