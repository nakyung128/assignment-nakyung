package com.nakyung.assignment_nakyung.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nakyung.assignment_nakyung.R

@Composable
fun SearchRoute(
    modifier: Modifier,
    searchViewModel: SearchViewModel = viewModel(),
    navigateToDetail: () -> Unit,
) {
    SearchScreen(
        modifier = modifier,
        searchRepo = {},
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchRepo: (String) -> Unit = {},
) {
    var keyword by remember { mutableStateOf("") }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                modifier = Modifier.weight(9f),
                value = keyword,
                onValueChange = {
                    keyword = it
                },
                placeholder = {
                    Text(text = "검색어를 입력해 주세요")
                },
                trailingIcon = {
                    Icon(
                        modifier =
                            Modifier
                                .weight(1f)
                                .size(size = 30.dp)
                                .clickable { },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "search",
                        tint = Color.Unspecified,
                    )
                },
                colors =
                    TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = Color.Black,
                    ),
            )
        }
    }
}
