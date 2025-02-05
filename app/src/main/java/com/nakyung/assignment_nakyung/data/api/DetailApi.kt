package com.nakyung.assignment_nakyung.data.api

import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import com.nakyung.assignment_nakyung.domain.model.RepoResponse
import com.nakyung.assignment_nakyung.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {
    @GET("repos/{owner}/{repo}")
    suspend fun getDetail(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): DetailResponse

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
    ): List<RepoResponse>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String,
    ): UserResponse
}
