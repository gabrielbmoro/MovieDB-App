package com.gabrielbmoro.moviedb.domain.di

import com.gabrielbmoro.moviedb.data.di.DataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@ComponentScan("com.gabrielbmoro.moviedb.domain")
@Module(includes = [DataModule::class])
class DomainModule
