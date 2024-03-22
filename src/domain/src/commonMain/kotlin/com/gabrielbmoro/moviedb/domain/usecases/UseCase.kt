package com.gabrielbmoro.moviedb.domain.usecases

/**
 * Use case should extend from this interface
 *
 * Use cases responsibilities:
 * - Provide access to the domain entities;
 * - Business rules
 * - Fetch information from repositories;
 *
 * They can be reused to other systems, because it is
 * not tied to the UI, and not tied to the data layer.
 */
interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}
