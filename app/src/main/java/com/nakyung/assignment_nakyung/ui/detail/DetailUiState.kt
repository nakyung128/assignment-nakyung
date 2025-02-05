package com.nakyung.assignment_nakyung.ui.detail

import androidx.compose.runtime.Stable

@Stable
interface DetailUiState {
    data class Success(
        val imgUrl: String,
        val username: String,
        val name: String,
        val starCount: Int,
        val watcherCount: Int,
        val forksCount: Int,
        val description: String,
        val topics: List<String>,
    ) : DetailUiState

    data object Loading : DetailUiState

    data class Error(
        val message: String,
    ) : DetailUiState
}
