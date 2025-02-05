package com.nakyung.assignment_nakyung.ui.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface DetailUiState {
    @Immutable
    data class Success(
        val imgUrl: String,
        val username: String,
        val name: String,
        val starCount: Int,
        val watcherCount: Int,
        val forksCount: Int,
        val description: String,
        val topics: List<String>,
        val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState.Hidden,
    ) : DetailUiState

    @Immutable
    data object Loading : DetailUiState

    @Immutable
    data class Error(
        val message: String,
    ) : DetailUiState
}

@Stable
sealed interface BottomSheetUiState {
    @Immutable
    data object Loading : BottomSheetUiState

    @Immutable
    data object Hidden : BottomSheetUiState

    @Immutable
    data class Error(
        val message: String,
    ) : BottomSheetUiState

    @Immutable
    data class Success(
        val imgUrl: String = "",
        val username: String = "",
        val followers: Int = 0,
        val following: Int = 0,
        val language: String = "",
        val repositories: Int = 0,
        val bio: String? = "",
    ) : BottomSheetUiState
}
