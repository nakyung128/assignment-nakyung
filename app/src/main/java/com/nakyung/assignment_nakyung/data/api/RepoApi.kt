package com.nakyung.assignment_nakyung.data.api

import com.nakyung.assignment_nakyung.domain.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoApi {
    @GET("search/repositories")
    suspend fun getRepoList(
        @Query("q") keyword: String,
    ): SearchResponse
}
