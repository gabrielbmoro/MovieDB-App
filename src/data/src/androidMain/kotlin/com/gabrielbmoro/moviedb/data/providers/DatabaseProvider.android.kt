package com.gabrielbmoro.moviedb.data.providers

import androidx.room.Room
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.DataBaseFactory
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dbFileName
import org.koin.mp.KoinPlatform

actual fun databaseInstance(): DataBaseFactory {
    return Room.databaseBuilder(
        KoinPlatform.getKoin().get(),
        DataBaseFactory::class.java,
        dbFileName
    ).build()
}
