package com.nakyung.assignment_nakyung.data.di

import com.nakyung.assignment_nakyung.data.api.DetailApi
import com.nakyung.assignment_nakyung.data.api.RepoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRepoApi(retrofit: Retrofit): RepoApi = retrofit.create()

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApi = retrofit.create()
}
