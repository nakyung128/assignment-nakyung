package com.nakyung.assignment_nakyung.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nakyung.assignment_nakyung.domain.model.Item
import com.nakyung.assignment_nakyung.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchRepository: SearchRepository,
    ) : ViewModel() {
        private val _pagingData = MutableStateFlow<PagingData<Item>>(PagingData.empty())
        val pagingData = _pagingData.asStateFlow()

        private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
        val uiState = _uiState.asStateFlow()

        var keyword = MutableStateFlow("")
            private set

        fun updateKeyword(newKeyword: String) {
            keyword.value = newKeyword
        }

        fun getSearchResult(keyword: String) {
            viewModelScope.launch {
                searchRepo(keyword)
                    .catch { e ->
                        _uiState.value = SearchUiState.Error(e.message ?: "")
                    }.collectLatest { pagingData ->
                        if (pagingData != PagingData.empty<Item>()) {
                            _pagingData.value = pagingData
                            _uiState.value = SearchUiState.Success
                        } else {
                            _uiState.value = SearchUiState.Error("검색 결과가 없습니다.")
                        }
                        _pagingData.value = pagingData
                    }
            }
        }

        private fun searchRepo(keyword: String): Flow<PagingData<Item>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 30,
                        prefetchDistance = 2,
                        enablePlaceholders = false,
                    ),
            ) {
                SearchPageSource(searchRepository, keyword)
            }.flow.cachedIn(viewModelScope)
    }
