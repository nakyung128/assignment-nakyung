package com.nakyung.assignment_nakyung.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nakyung.assignment_nakyung.components.ErrorScreen
import com.nakyung.assignment_nakyung.components.LoadingDialog
import com.nakyung.assignment_nakyung.components.TopicChip
import com.nakyung.assignment_nakyung.core.util.toKFormat
import com.nakyung.assignment_nakyung.ui.theme.LightBlue

@Composable
fun DetailRoute(
    modifier: Modifier,
    owner: String,
    repo: String,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getDetail(owner, repo)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HandleDetailUi(
        modifier = modifier,
        uiState = uiState,
        onBottomSheetDismiss = viewModel::closeBottomSheet,
        onShowBottomSheet = viewModel::loadBottomSheetData,
    )
}

@Composable
fun HandleDetailUi(
    modifier: Modifier,
    uiState: DetailUiState,
    onBottomSheetDismiss: () -> Unit,
    onShowBottomSheet: (String) -> Unit,
) {
    when (uiState) {
        is DetailUiState.Success -> {
            DetailScreen(
                modifier = modifier,
                imgUrl = uiState.imgUrl,
                name = uiState.name,
                username = uiState.username,
                starCount = uiState.starCount,
                watcherCount = uiState.watcherCount,
                forksCount = uiState.forksCount,
                description = uiState.description,
                topics = uiState.topics,
                bottomSheetUiState = uiState.bottomSheetUiState,
                onBottomSheetDismiss = onBottomSheetDismiss,
                onShowBottomSheet = onShowBottomSheet,
            )
        }

        DetailUiState.Loading -> {
            LoadingDialog()
        }

        is DetailUiState.Error -> {
            ErrorScreen(message = uiState.message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    imgUrl: String,
    name: String,
    username: String,
    starCount: Int,
    watcherCount: Int,
    forksCount: Int,
    description: String,
    topics: List<String>,
    bottomSheetUiState: BottomSheetUiState,
    onShowBottomSheet: (String) -> Unit,
    onBottomSheetDismiss: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = name,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(13.dp))
            TopicChip(topics = topics)
            Spacer(modifier = Modifier.height(22.dp))
            HorizontalDivider(color = Color.LightGray)
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    ShowCount(title = "Star", count = starCount)
                    ShowCount(title = "Watchers", count = watcherCount)
                    ShowCount(title = "Forks", count = forksCount)
                }
            }
            HorizontalDivider(color = Color.LightGray)
            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier =
                        Modifier
                            .size(size = 35.dp)
                            .clip(shape = CircleShape),
                    model = imgUrl,
                    contentDescription = "user_profile",
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = username,
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                )
                Box(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onShowBottomSheet(username) },
                    colors =
                        ButtonColors(
                            containerColor = LightBlue,
                            contentColor = Color.White,
                            disabledContentColor = Color.DarkGray,
                            disabledContainerColor = Color.Gray,
                        ),
                ) {
                    Text(
                        text = "more",
                    )
                }
            }
            HorizontalDivider(color = Color.LightGray)
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "Description",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = description,
                color = Color.DarkGray,
                fontSize = 18.sp,
            )
        }

        if (bottomSheetUiState != BottomSheetUiState.Hidden) {
            ModalBottomSheet(
                containerColor = Color.White,
                onDismissRequest = onBottomSheetDismiss,
                dragHandle = null,
            ) {
                when (bottomSheetUiState) {
                    is BottomSheetUiState.Success -> {
                        UserInfo(
                            imgUrl = imgUrl,
                            username = username,
                            followers = bottomSheetUiState.followers,
                            following = bottomSheetUiState.following,
                            language = bottomSheetUiState.language,
                            repositories = bottomSheetUiState.repositories,
                            bio = bottomSheetUiState.bio ?: "no bio",
                        )
                    }

                    is BottomSheetUiState.Error -> {
                        ErrorScreen(message = bottomSheetUiState.message)
                    }

                    BottomSheetUiState.Loading -> {
                        LoadingDialog(
                            modifier = Modifier.heightIn(max = 400.dp),
                        )
                    }

                    BottomSheetUiState.Hidden -> {}
                }
            }
        }
    }
}

@Composable
fun ShowCount(
    title: String,
    count: Int,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = count.toKFormat(),
            fontSize = 18.sp,
            color = Color.Gray,
        )
    }
}

@Composable
fun UserInfo(
    imgUrl: String,
    username: String,
    followers: Int,
    following: Int,
    language: String,
    repositories: Int,
    bio: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier.padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .size(size = 50.dp)
                        .clip(shape = CircleShape),
                model = imgUrl,
                contentDescription = "user_profile",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = username,
                fontSize = 30.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        InfoRow(title = "Followers", content = followers.toKFormat())
        InfoRow(title = "Following", content = following.toKFormat())
        InfoRow(title = "Language", content = language)
        InfoRow(title = "Repositories", content = repositories.toKFormat())
        InfoRow(title = "Bio", content = bio)
    }
}

@Composable
fun InfoRow(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier.padding(bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = content,
            color = Color.Gray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    UserInfo(
        imgUrl = "",
        username = "owncloud",
        followers = 954,
        following = 0,
        language = "Kotlin, Java, C++, JavaScript, Python",
        repositories = 168,
        bio = "asdfasdfasdf",
    )
}
