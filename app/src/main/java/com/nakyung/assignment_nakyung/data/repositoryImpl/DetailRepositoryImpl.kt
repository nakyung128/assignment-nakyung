package com.nakyung.assignment_nakyung.data.repositoryImpl

import com.nakyung.assignment_nakyung.data.api.DetailApi
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl
    @Inject
    constructor(
        private val api: DetailApi,
    ) : DetailRepository {
        override suspend fun getDetail(
            owner: String,
            repo: String,
        ): Flow<Result<DetailResponse>> =
            flow {
                try {
                    val response = api.getDetail(owner, repo)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.message ?: ""))
                }
            }
    }
