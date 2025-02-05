package com.nakyung.assignment_nakyung.domain.repository

import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import com.nakyung.assignment_nakyung.domain.model.RepoResponse
import com.nakyung.assignment_nakyung.domain.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetail(
        owner: String,
        repo: String,
    ): Flow<Result<DetailResponse>>

    suspend fun getRepos(username: String): Flow<Result<List<RepoResponse>>>

    suspend fun getUserInfo(username: String): Flow<Result<UserResponse>>
}
