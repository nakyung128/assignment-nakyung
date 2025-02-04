package com.nakyung.assignment_nakyung.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.Item
import com.nakyung.assignment_nakyung.domain.repository.SearchRepository
import javax.inject.Inject

class SearchPageSource
    @Inject
    constructor(
        private val searchRepository: SearchRepository,
        private val keyword: String,
    ) : PagingSource<Int, Item>() {
        override fun getRefreshKey(state: PagingState<Int, Item>): Int? = state.anchorPosition

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> =
            try {
                val page = params.key ?: 1
                val response = searchRepository.searchRepo(keyword = keyword, page = page)

                when (response) {
                    is Result.Success -> {
                        val data = response.data
                        LoadResult.Page(
                            data = data.items,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (data.items.isEmpty()) null else page + 1,
                        )
                    }

                    is Result.Error -> {
                        LoadResult.Error(Exception(response.message))
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
    }
