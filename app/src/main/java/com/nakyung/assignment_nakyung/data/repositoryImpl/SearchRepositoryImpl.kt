package com.nakyung.assignment_nakyung.data.repositoryImpl

import com.nakyung.assignment_nakyung.data.api.RepoApi
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.SearchResponse
import com.nakyung.assignment_nakyung.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl
    @Inject
    constructor(
        private val api: RepoApi,
    ) : SearchRepository {
        override suspend fun searchRepo(
            keyword: String,
            page: Int,
        ): Result<SearchResponse> =
            try {
                val response = api.getRepoList(keyword, page)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error")
            }
    }
