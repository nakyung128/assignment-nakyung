package com.nakyung.assignment_nakyung

import com.nakyung.assignment_nakyung.domain.Result
import com.nakyung.assignment_nakyung.domain.model.DetailResponse
import com.nakyung.assignment_nakyung.domain.model.Owner
import com.nakyung.assignment_nakyung.domain.model.RepoResponse
import com.nakyung.assignment_nakyung.domain.model.UserResponse
import com.nakyung.assignment_nakyung.domain.repository.DetailRepository
import com.nakyung.assignment_nakyung.ui.detail.BottomSheetUiState
import com.nakyung.assignment_nakyung.ui.detail.DetailUiState
import com.nakyung.assignment_nakyung.ui.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val detailRepository: DetailRepository = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = DetailViewModel(detailRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `레포지토리 상세 정보 조회 성공 시 UI 상태가 Success로 변경된다`() =
        runTest {
            // Given : 가짜 응답 데이터
            val fakeDetail =
                DetailResponse(
                    name = "TestRepo",
                    owner = Owner("test_user", "https://example.com/avatar.png"),
                    description = "test description",
                    stargazersCount = 100,
                    watchersCount = 50,
                    forksCount = 20,
                    topics = listOf("topic1", "topic2"),
                )

            // When
            coEvery {
                detailRepository.getDetail("test_user", "TestRepo")
            } returns flowOf(Result.Success(fakeDetail))

            viewModel.getDetail("test_user", "TestRepo")
            advanceUntilIdle()

            val expectedState =
                DetailUiState.Success(
                    imgUrl = "https://example.com/avatar.png",
                    username = "test_user",
                    name = "TestRepo",
                    starCount = 100,
                    watcherCount = 50,
                    forksCount = 20,
                    description = "test description",
                    topics = listOf("topic1", "topic2"),
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `유저 정보 조회 성공 시 바텀시트 UI 상태가 Success로 변경된다`() =
        runTest {
            val fakeData =
                UserResponse(
                    login = "test_user",
                    avatarUrl = "https://example.com/avatar.png",
                    bio = "test bio",
                    publicRepos = 5,
                    followers = 100,
                    following = 50,
                )

            val fakeDetail =
                DetailResponse(
                    name = "TestRepo",
                    owner = Owner("test_user", "https://example.com/avatar.png"),
                    description = "test description",
                    stargazersCount = 100,
                    watchersCount = 50,
                    forksCount = 20,
                    topics = listOf("topic1", "topic2"),
                )

            // 초기 상태 설정 : DetailUiState.Success
            coEvery {
                detailRepository.getDetail("test_user", "TestRepo")
            } returns flowOf(Result.Success(fakeDetail))

            viewModel.getDetail("test_user", "TestRepo")
            advanceUntilIdle()

            // When: BottomSheet 데이터 로드
            coEvery {
                detailRepository.getUserInfo("test_user")
            } returns flowOf(Result.Success(fakeData))

            coEvery {
                detailRepository.getRepos("test_user")
            } returns flowOf(Result.Success(listOf(RepoResponse(language = "Kotlin"))))

            viewModel.loadBottomSheetData("test_user")
            advanceUntilIdle()

            // Then
            val expectedState =
                DetailUiState.Success(
                    imgUrl = "https://example.com/avatar.png",
                    username = "test_user",
                    name = "TestRepo",
                    starCount = 100,
                    watcherCount = 50,
                    forksCount = 20,
                    description = "test description",
                    topics = listOf("topic1", "topic2"),
                    bottomSheetUiState =
                        BottomSheetUiState.Success(
                            imgUrl = "https://example.com/avatar.png",
                            username = "test_user",
                            followers = 100,
                            following = 50,
                            language = "Kotlin",
                            repositories = 5,
                            bio = "test bio",
                        ),
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }

    @Test
    fun `바텀시트를 닫으면 Hidden 상태가 된다`() =
        runTest {
            // Given: 초기 상태를 Success로 설정
            val fakeDetail =
                DetailResponse(
                    name = "TestRepo",
                    owner = Owner("test_user", "https://example.com/avatar.png"),
                    description = "test description",
                    stargazersCount = 100,
                    watchersCount = 50,
                    forksCount = 20,
                    topics = listOf("topic1", "topic2"),
                )

            coEvery {
                detailRepository.getDetail("test_user", "TestRepo")
            } returns flowOf(Result.Success(fakeDetail))

            // 초기 상태 설정
            viewModel.getDetail("test_user", "TestRepo")
            advanceUntilIdle()

            // When: 바텀시트 닫기
            viewModel.closeBottomSheet()

            // Then: bottomSheetUiState가 Hidden인지 확인
            val currentState = viewModel.uiState.value
            assertTrue(currentState is DetailUiState.Success)
            assertEquals(
                BottomSheetUiState.Hidden,
                (currentState as DetailUiState.Success).bottomSheetUiState,
            )
        }
}
