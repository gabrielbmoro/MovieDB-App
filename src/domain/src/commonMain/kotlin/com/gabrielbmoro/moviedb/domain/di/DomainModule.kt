package com.gabrielbmoro.moviedb.domain.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

val domainModule = DomainModule().module

@Module
@ComponentScan("com.gabrielbmoro.moviedb.domain.usecases")
class DomainModule
