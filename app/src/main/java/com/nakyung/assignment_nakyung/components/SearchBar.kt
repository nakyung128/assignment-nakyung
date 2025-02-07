package com.nakyung.assignment_nakyung.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nakyung.assignment_nakyung.R

@Composable
fun SearchBar(
    keyword: String,
    onKeywordChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val colors = MaterialTheme.colorScheme

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
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier =
                    Modifier
                        .size(size = 30.dp)
                        .clickable {
                            onSearchClick()
                            keyboardController?.hide()
                        },
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = Color.Unspecified,
            )
        },
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = colors.background,
                focusedContainerColor = colors.background,
                unfocusedIndicatorColor = colors.tertiary,
                focusedIndicatorColor = colors.primary,
                focusedTextColor = colors.primary,
            ),
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = {
                    onSearchClick()
                    keyboardController?.hide()
                },
            ),
    )
}
