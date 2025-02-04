package com.nakyung.assignment_nakyung.domain.repository

import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchRepo(keyword: String): Flow<Result<SearchResponse>>
}
