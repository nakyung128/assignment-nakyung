package com.nakyung.assignment_nakyung.domain.model

data class Item(
    val name: String,
    val owner: Owner,
    val description: String,
    val stargazersCount: Int,
    val language: String?,
)
