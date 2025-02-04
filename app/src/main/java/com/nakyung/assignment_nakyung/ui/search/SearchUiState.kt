package com.nakyung.assignment_nakyung.ui.search

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SearchUiState {
    @Immutable
    data object Init : SearchUiState

    @Immutable
    data object Success : SearchUiState

    @Immutable
    data class Error(
        val message: String,
    ) : SearchUiState
}
