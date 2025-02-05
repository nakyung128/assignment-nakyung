package com.nakyung.assignment_nakyung.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nakyung.assignment_nakyung.R
import com.nakyung.assignment_nakyung.domain.model.Item
import com.nakyung.assignment_nakyung.ui.util.toKFormat

@Composable
fun RepoListItem(
    modifier: Modifier = Modifier,
    item: Item,
    onClick: (String, String) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick(item.owner.login, item.name) }
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .background(color = Color.Unspecified),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .size(size = 25.dp)
                        .clip(shape = CircleShape),
                model = item.owner.avatarUrl,
                contentDescription = "owner_image",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.owner.login,
                color = Color.Gray,
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = item.name,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (item.description != null) {
            Text(
                text = item.description,
                color = Color.DarkGray,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "star",
                tint = Color.Yellow,
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = item.stargazersCount.toKFormat(),
                color = Color.Gray,
            )
            if (item.language != null) {
                Spacer(modifier = Modifier.width(8.dp))
                LanguageWithCircle(language = item.language)
            }
        }
    }
}
