package com.nakyung.assignment_nakyung.data.repositoryImpl

import com.nakyung.assignment_nakyung.data.api.RepoApi
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.SearchResponse
import com.nakyung.assignment_nakyung.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl
    @Inject
    constructor(
        private val api: RepoApi,
    ) : SearchRepository {
        override suspend fun searchRepo(keyword: String): Flow<Result<SearchResponse>> =
            flow {
                try {
                    val response = api.getRepoList(keyword)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.message ?: ""))
                }
            }
    }
