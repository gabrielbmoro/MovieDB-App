package com.gabrielbmoro.moviedb.domain.usecases

/**
 * Use case should extend from this interface
 *
 * - Allows access the domain entities;
 * - Fetch information from repositories;
 * - They can be reused to other systems, because it is not tied to the UI, and not tied to the data layer.
 */
interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}
