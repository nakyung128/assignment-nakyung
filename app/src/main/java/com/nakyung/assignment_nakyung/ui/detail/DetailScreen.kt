package com.nakyung.assignment_nakyung.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailRoute(
    modifier: Modifier,
    owner: String,
    repo: String,
) {
    DetailScreen()
}

@Composable
fun DetailScreen() {
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen()
}
