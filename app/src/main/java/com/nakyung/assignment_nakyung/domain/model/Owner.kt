package com.nakyung.assignment_nakyung.domain.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
