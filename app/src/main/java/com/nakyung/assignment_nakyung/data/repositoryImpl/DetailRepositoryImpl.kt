package com.nakyung.assignment_nakyung.data.repositoryImpl

import com.nakyung.assignment_nakyung.data.api.DetailApi
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import com.nakyung.assignment_nakyung.domain.model.RepoResponse
import com.nakyung.assignment_nakyung.domain.model.UserResponse
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
import com.nakyung.assignment_nakyung.core.util.toErrorMessage
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
                    emit(Result.Error(e.toErrorMessage()))
                }
            }

        override suspend fun getRepos(username: String): Flow<Result<List<RepoResponse>>> =
            flow {
                try {
                    val response = api.getUserRepos(username)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.toErrorMessage()))
                }
            }

        override suspend fun getUserInfo(username: String): Flow<Result<UserResponse>> =
            flow {
                try {
                    val response = api.getUserInfo(username)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e.toErrorMessage()))
                }
            }
    }
