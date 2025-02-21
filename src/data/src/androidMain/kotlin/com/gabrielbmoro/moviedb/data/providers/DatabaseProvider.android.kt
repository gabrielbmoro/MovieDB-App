package com.gabrielbmoro.moviedb.data.providers

import androidx.room.Room
import org.koin.mp.KoinPlatform

actual fun databaseInstance(): AppDatabase {
    return Room.databaseBuilder(
        KoinPlatform.getKoin().get(),
        AppDatabase::class.java,
        dbFileName,
    ).build()
}
