package com.nakyung.assignment_nakyung.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nakyung.assignment_nakyung.R

@Composable
fun SearchBar(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        value = keyword,
        onValueChange = onKeywordChanged,
        placeholder = {
            Text(text = "검색어를 입력해 주세요")
        },
        trailingIcon = {
            Icon(
                modifier =
                    Modifier
                        .size(size = 30.dp)
                        .clickable {
                            onSearchClick()
                        },
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
