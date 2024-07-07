package com.gabrielbmoro.moviedb.data.providers

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.DataBaseFactory
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dbFileName
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}

actual fun databaseInstance(): DataBaseFactory {
    val dbFile = "${fileDirectory()}/$dbFileName"
    return Room.databaseBuilder<DataBaseFactory>(
        name = dbFile,
        factory =  { DataBaseFactory::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
