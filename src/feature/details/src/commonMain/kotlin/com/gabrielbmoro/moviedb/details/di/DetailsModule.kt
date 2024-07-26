package com.gabrielbmoro.moviedb.details.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val featureDetailsModule = DetailsModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.details.ui")
class DetailsModule