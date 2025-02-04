package com.nakyung.assignment_nakyung.domain.model

import com.google.gson.annotations.SerializedName

data class Item(
    val name: String,
    val owner: Owner,
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val language: String?,
)
