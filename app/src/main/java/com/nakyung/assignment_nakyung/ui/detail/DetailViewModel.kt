package com.nakyung.assignment_nakyung.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.RepoResponse
import com.nakyung.assignment_nakyung.domain.model.UserResponse
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
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

        fun loadBottomSheetData(username: String) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    if (currentState is DetailUiState.Success) {
                        currentState.copy(
                            bottomSheetUiState = BottomSheetUiState.Loading,
                        )
                    } else {
                        currentState
                    }
                }
                combine(
                    detailRepository.getRepos(username),
                    detailRepository.getUserInfo(username),
                ) { repoResult, userResult ->
                    createBottomSheet(repoResult, userResult)
                }.collect { newState ->
                    _uiState.update { state ->
                        if (state is DetailUiState.Success) {
                            state.copy(
                                bottomSheetUiState = newState,
                            )
                        } else {
                            state
                        }
                    }
                }
            }
        }

        private fun createBottomSheet(
            repoResult: Result<List<RepoResponse>>,
            userResult: Result<UserResponse>,
        ): BottomSheetUiState =
            when {
                repoResult is Result.Success && userResult is Result.Success -> {
                    val repoData = repoResult.data
                    val userData = userResult.data
                    val languageString =
                        repoData
                            .mapNotNull { it.language }
                            .toSet()
                            .joinToString(", ")

                    BottomSheetUiState.Success(
                        imgUrl = userData.avatarUrl,
                        username = userData.login,
                        followers = userData.followers,
                        following = userData.following,
                        language = languageString,
                        repositories = userData.publicRepos,
                        bio = userData.bio,
                    )
                }

                repoResult is Result.Error -> BottomSheetUiState.Error(repoResult.message)
                userResult is Result.Error -> BottomSheetUiState.Error(userResult.message)
                else -> BottomSheetUiState.Error("Unknown Error")
            }

        fun closeBottomSheet() {
            _uiState.update { currentState ->
                if (currentState is DetailUiState.Success) {
                    currentState.copy(
                        bottomSheetUiState = BottomSheetUiState.Hidden,
                    )
                } else {
                    currentState
                }
            }
        }
    }
