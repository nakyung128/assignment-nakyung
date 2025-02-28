package com.nakyung.assignment_nakyung.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nakyung.assignment_nakyung.components.ErrorScreen
import com.nakyung.assignment_nakyung.components.LoadingDialog
import com.nakyung.assignment_nakyung.components.RepoListItem
import com.nakyung.assignment_nakyung.components.SearchBar
import com.nakyung.assignment_nakyung.domain.model.Item

@Composable
fun SearchRoute(
    modifier: Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (String, String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val resultList = viewModel.pagingData.collectAsLazyPagingItems()
    val keyword by viewModel.keyword.collectAsStateWithLifecycle()

    HandleSearchUi(
        modifier = modifier,
        keyword = keyword,
        searchRepo = viewModel::getSearchResult,
        uiState = uiState,
        resultList = resultList,
        navigateToDetail = navigateToDetail,
        updateKeyword = viewModel::updateKeyword,
    )
}

@Composable
fun HandleSearchUi(
    modifier: Modifier,
    keyword: String,
    updateKeyword: (String) -> Unit,
    searchRepo: (String) -> Unit,
    uiState: SearchUiState,
    resultList: LazyPagingItems<Item>,
    navigateToDetail: (String, String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = colors.background),
    ) {
        SearchBar(
            keyword = keyword,
            onKeywordChanged = {
                updateKeyword(it)
            },
            onSearchClick = {
                searchRepo(keyword)
            },
        )

        // UI 상태에 따라 화면 처리
        when (uiState) {
            SearchUiState.Init -> {} // 아무것도 하지 않은 기본 상태

            SearchUiState.Success -> {
                SearchScreen(
                    modifier = modifier,
                    resultList = resultList,
                    navigateToDetail = navigateToDetail,
                )
            }

            is SearchUiState.Error -> {
                ErrorScreen(
                    modifier = modifier,
                    message = uiState.message,
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    resultList: LazyPagingItems<Item>,
    navigateToDetail: (String, String) -> Unit,
) {
    when {
        resultList.loadState.refresh is LoadState.Loading -> {
            LoadingDialog()
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
            ) {
                items(resultList.itemCount) { index ->
                    val item = resultList[index]
                    item?.let {
                        RepoListItem(
                            item = it,
                            onClick = navigateToDetail,
                        )
                        HorizontalDivider(
                            color = Color.LightGray,
                        )
                    }
                }

                if (resultList.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
