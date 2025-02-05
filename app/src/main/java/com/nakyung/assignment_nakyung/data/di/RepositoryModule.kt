package com.nakyung.assignment_nakyung.data.di

import com.nakyung.assignment_nakyung.data.repositoryImpl.DetailRepositoryImpl
import com.nakyung.assignment_nakyung.data.repositoryImpl.SearchRepositoryImpl
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
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

    @Binds
    @Singleton
    fun bindDetailRepository(detailRepositoryImpl: DetailRepositoryImpl): DetailRepository
}
