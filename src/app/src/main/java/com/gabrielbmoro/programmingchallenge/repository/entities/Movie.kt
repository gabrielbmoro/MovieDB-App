package com.gabrielbmoro.programmingchallenge.repository.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_movies")
data class Movie(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("vote_count") val votes: Int?,
        @SerializedName("video") var isVideo: Boolean?,
        @SerializedName("vote_average") var votesAverage: Float?,
        @SerializedName("title") var title: String?,
        @SerializedName("popularity") var popularity: Float?,
        @SerializedName("poster_path") var posterPath: String?,
        @SerializedName("original_language") var originalLanguage: String?,
        @SerializedName("original_title") var originalTitle: String?,
        @SerializedName("backdrop_path") var backdropPath: String?,
        @SerializedName("adult") var isAdult: Boolean?,
        @SerializedName("overview") var overview: String?,
        @SerializedName("release_date") var releaseDate: String?,
        var isFavorite: Boolean = false
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readString(),
                parcel.readValue(Float::class.java.classLoader) as? Float,
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
                parcel.readString(),
                parcel.readString(),
                parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeValue(votes)
                parcel.writeValue(isVideo)
                parcel.writeValue(votesAverage)
                parcel.writeString(title)
                parcel.writeValue(popularity)
                parcel.writeString(posterPath)
                parcel.writeString(originalLanguage)
                parcel.writeString(originalTitle)
                parcel.writeString(backdropPath)
                parcel.writeValue(isAdult)
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