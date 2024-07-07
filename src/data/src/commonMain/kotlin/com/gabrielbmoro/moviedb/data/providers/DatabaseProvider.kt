package com.gabrielbmoro.moviedb.data.providers

import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.DataBaseFactory

expect fun databaseInstance(): DataBaseFactory
