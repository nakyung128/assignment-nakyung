package com.nakyung.assignment_nakyung.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nakyung.assignment_nakyung.ui.theme.LightGray

@Composable
fun TopicChip(topics: List<String>) {
    val state = rememberLazyListState()

    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(topics.size) { index ->
            Chip(topic = topics[index])
        }
    }
}

@Composable
fun Chip(topic: String) {
    Text(
        modifier =
            Modifier
                .background(
                    color = LightGray,
                    shape = RoundedCornerShape(15.dp),
                ).padding(horizontal = 10.dp, vertical = 2.dp),
        text = topic,
        fontSize = 14.sp,
        color = Color.Gray,
        fontWeight = FontWeight.W400,
    )
}

@Preview(showBackground = true)
@Composable
fun TopicChipPreview() {
    TopicChip(listOf("android", "kotlin", "owncloud"))
}
