package com.nakyung.assignment_nakyung.domain.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("public_repos")
    val publicRepos: Int,
    val bio: String?,
    val followers: Int,
    val following: Int,
)
