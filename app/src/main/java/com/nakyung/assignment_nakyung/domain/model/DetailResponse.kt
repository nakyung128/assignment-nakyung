package com.nakyung.assignment_nakyung.domain.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    val name: String,
    val owner: Owner,
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    val topics: List<String>,
)
