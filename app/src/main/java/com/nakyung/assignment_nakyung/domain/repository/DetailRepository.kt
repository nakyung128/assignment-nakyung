package com.nakyung.assignment_nakyung.domain.repository

import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetail(
        owner: String,
        repo: String,
    ): Flow<Result<DetailResponse>>
}
