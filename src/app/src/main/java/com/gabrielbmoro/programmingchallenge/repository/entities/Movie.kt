package com.gabrielbmoro.programmingchallenge.repository.entities

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val votesAverage: Float,
    val title: String,
    val imageUrl: String,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean,
    val language: String,
    val popularity : Float
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() == 1,
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(votesAverage)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeInt(
            if(isFavorite) 1 else 0
        )
        parcel.writeString(language)
        parcel.writeFloat(popularity)
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