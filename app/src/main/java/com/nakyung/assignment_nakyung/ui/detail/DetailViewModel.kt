package com.nakyung.assignment_nakyung.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val detailRepository: DetailRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
        val uiState = _uiState.asStateFlow()

        fun getDetail(
            owner: String,
            repo: String,
        ) {
            viewModelScope.launch {
                detailRepository.getDetail(owner, repo).collectLatest { response ->
                    when (response) {
                        is Result.Success -> {
                            val data = response.data

                            _uiState.value =
                                DetailUiState.Success(
                                    imgUrl = data.owner.avatarUrl,
                                    username = data.owner.login,
                                    name = data.name,
                                    starCount = data.stargazersCount,
                                    watcherCount = data.watchersCount,
                                    forksCount = data.forksCount,
                                    description = data.description ?: "no description",
                                    topics = data.topics,
                                )
                        }

                        is Result.Error -> {
                            _uiState.value = DetailUiState.Error(response.message)
                        }
                    }
                }
            }
        }
    }
