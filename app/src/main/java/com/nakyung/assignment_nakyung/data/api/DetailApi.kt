package com.nakyung.assignment_nakyung.data.api

import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {
    @GET("repos/{owner}/{repo}")
    suspend fun getDetail(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): DetailResponse
}