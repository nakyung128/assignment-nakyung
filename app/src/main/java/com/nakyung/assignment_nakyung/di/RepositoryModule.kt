package com.nakyung.assignment_nakyung.di

import com.nakyung.assignment_nakyung.data.repositoryImpl.SearchRepositoryImpl
import com.nakyung.assignment_nakyung.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}
