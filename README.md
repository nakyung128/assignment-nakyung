# 📌 SOOP Android 앱 개발 공채 과제

## 📖 프로젝트 개요
이 프로젝트는 GitHub API를 활용하여 레포지토리 목록을 조회하고 레포지토리 상세 정보 및 사용자 정보를 제공하는 안드로이드 애플리케이션입니다. 🙂  
Android Compose를 사용해 UI를 선언형으로 구현하였고, 클린 아키텍처 기반으로 확장성과 유지보수성을 고려하여 설계하였습니다.

<br>

## 📂 프로젝트 구조
**클린 아키텍처(Clean Architecture)** 원칙에 따라 관심사를 분리해 `data`, `domain`, `ui` 세 개의 주요 레이어로 구성하였습니다.

```
📂 assignment_nakyung
 ├── 📂 components   # 공통 UI 컴포넌트 모음
 ├── 📂 core
 │    ├── 📂 util      # 숫자 형변환, 상태 코드별 메시지 처리 (공통 코드)
 ├── 📂 data         # 데이터 레이어 
 │    ├── 📂 api     # Retrofit API 인터페이스 정의
 │    ├── 📂 di      # Hilt 의존성 주입 모듈
 │    ├── 📂 repositoryImpl # Repository 인터페이스 구현체
 ├── 📂 domain       # 도메인 레이어
 │    ├── 📂 model   # 데이터 클래스
 │    ├── 📂 repository # Repository 인터페이스 정의
 │    ├── Result.kt # API 응답 래퍼 클래스
 ├── 📂 ui           # UI 레이어
 │    ├── 📂 detail  # 상세 페이지
 │    ├── 📂 main    # 최상위 스크린
 │    ├── 📂 navigation # 네비게이션 구성
 │    ├── 📂 search  # 메인 페이지 (사용자 검색 화면)
 │    ├── 📂 theme  # 테마 관련
 │    ├── 📝 MainActivity.kt
 ├── 📝 MyApplication.kt
```

✅ **구조의 장점**
- 클린 아키텍처를 적용하여 **유지보수성과 확장**이 용이하도록 하였습니다.
- **UI, 도메인, 데이터 레이어의 분리**를 통해 관심사를 분리하였습니다.
- **Hilt를 활용한 의존성 주입**으로 효율적인 DI 구현 및 테스트 용이성을 높였습니다.

<br>

## 🚀 주요 기능
1. **레포지토리 검색 기능**
   - 키워드를 검색하면 관련 레포지토리가 **리스트로 제공**됩니다.
   - **Paging3 라이브러리**를 사용하여 데이터가 페이지 당 30개씩 자동으로 로드됩니다.
   - 리스트 중 하나를 클릭하면 **레포지토리 상세 페이지로 이동**합니다.
2. **레포지토리 상세 정보 제공**
   - 레포지토리명, 설명, 사용 언어, 스타 개수, 포크 수, 토픽 등을 확인할 수 있습니다.
   - 토픽 리스트는 **LazyRow**로 구현하여, 개수가 많아질 경우 가로 스크롤을 할 수 있도록 하였습니다.
2. **GitHub 유저 정보 제공**
   - more 버튼을 클릭하면 사용자 정보가 담긴 **바텀 시트**가 나옵니다.
   - 레포지토리 개수, 팔로워 수, 팔로잉 수, 바이오를 보여줍니다.
   - 이때, **바이오가 없는 경우 "no bio"가 출력**되도록 처리하였습니다.
3. **유저 레포지토리 개수 제공**
   - 바텀시트(유저 정보)에 함께 제공되는 정보입니다.
   - 사용자의 레포지토리 목록을 받아 지금까지 사용한 언어를 한눈에 보여줍니다.
   - **mapNotNull**을 이용해 List에서 중복된 언어와 Null은 제거하였습니다.
5. **다크모드 지원**
   - 라이트모드 / 다크모드에 따라 색상이 변경됩니다.

<br>

## 🔎 핵심 코드 설명
### 1️⃣ Hilt를 이용한 의존성 주입
Hilt를 이용해 의존성을 효율적으로 주입하였습니다. API 통신을 위한 Retrofit 인스턴스, Repository 인터페이스와 구현체를 Hilt 모듈을 통해 제공하고 있습니다. 👀 모든 모듈은 싱글톤으로, 앱의 전체 생명주기 동안 단일 인스턴스를 유지하도록 하였습니다.  
<br>
🤷 **왜 싱글톤으로 했나요?** <br>
☝️ 앱 전체에서 **동일한 네트워크 설정**을 유지할 수 있습니다. <br>
✌️ 여러 곳에서 동일한 인스턴스를 사용하기 때문에 한 번만 생성하여 재사용하는 것이 **리소스를 절약**하는 방법이라고 생각하였습니다.

#### 📍 Network 모듈
`NetworkModule`에서는 Retrofit과 OkHttpClient를 설정하여 네트워크 통신을 수행하도록 하였습니다.

```Kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.github.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃 30초 설정
            .readTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃 30초 설정
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // 로깅 인터셉터 설정
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // GsonConverter 추가
            .client(okHttpClient) // OkHttpClient 주입
            .build()
}
```
#### 📍 API 모듈
`ApiModule`에서는 API 인스턴스 생성 방법을 제공합니다.

```Kotlin
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRepoApi(retrofit: Retrofit): RepoApi = retrofit.create()

    ...
}
```

#### 📍 Repository 모듈
`RepositoryModule`에서는 Repository 인터페이스와 구현체를 `@Binds`를 이용해 주입하였습니다.

```Kotlin
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    ...
}
```

### 2️⃣ 페이징 처리
`Paging3` 라이브러리를 사용해 검색 결과를 효율적으로 관리하였습니다. `SearchPageSource`에서 API 호출을 통해 데이터를 가져오고, `Pager`를 이용해 `Flow<PagingData<Item>>` 형태로 제공합니다.

#### 📍 SearchPageSoure
`PagingSource`를 상속받아 API 응답을 `LoadResult` 형식으로 반환합니다.

```Kotlin
class SearchPageSource
    @Inject
    constructor(
        private val searchRepository: SearchRepository,
        private val keyword: String,
    ) : PagingSource<Int, Item>() {
        override fun getRefreshKey(state: PagingState<Int, Item>): Int? = state.anchorPosition

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> =
            try {
                val page = params.key ?: 1 // key가 null인 경우 기본값인 1로 설정
                val response = searchRepository.searchRepo(keyword = keyword, page = page)

                when (response) {
                    is Result.Success -> {
                        val data = response.data
                        LoadResult.Page(
                            ...
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
```

#### 📍 ViewModel에서 페이징 데이터 관리
- `getSearchResult`는 페이징 데이터를 받아 처리하고, 에러 처리 및 상태를 관리합니다.
- `searchRepo`는 `Pager`를 이용해 페이징 처리를 합니다. `cachedIn`을 사용해 페이징 데이터가 viewModelScope에 연결된 캐시에 저장되고, 불필요한 네트워크 요청을 줄일 수 있습니다.

```Kotlin
fun getSearchResult(keyword: String) {
            viewModelScope.launch {
                searchRepo(keyword)
                    .catch { e ->
                        _uiState.value = SearchUiState.Error(e.message ?: "")
                    }.collectLatest { pagingData ->
                        if (pagingData != PagingData.empty<Item>()) {
                            _pagingData.value = pagingData
                            _uiState.value = SearchUiState.Success
                        } else {
                            _uiState.value = SearchUiState.Error("검색 결과가 없습니다.")
                        }
                        _pagingData.value = pagingData
                    }
            }
        }

 private fun searchRepo(keyword: String): Flow<PagingData<Item>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 30,
                        prefetchDistance = 2,
                        enablePlaceholders = false,
                    ),
            ) {
                SearchPageSource(searchRepository, keyword)
            }.flow.cachedIn(viewModelScope)
```

### 3️⃣ BottomSheet 상태 관리 방법
- `BottomSheetUiState`를 `DetailUiState`의 `Success` 상태에 포함해 내부에서 관리하였습니다.
- `BottomSheetUiState`를 위한 별도의 MutableStateFlow가 불필요해지므로 상태 관리 변수를 줄이고, UI가 하나의 상태만 바라보도록 하여 단일 상태 원칙(SSOT)을 유지하기 위해 이러한 방식을 택하였습니다.

#### 📍 DetailUiState와 BottomSheetUiState
```Kotlin
@Stable
sealed interface DetailUiState {
    @Immutable
    data class Success(
        val imgUrl: String,
        val username: String,
        val name: String,
        ... 
        val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState.Hidden,
    ) : DetailUiState

    ...
}

@Stable
sealed interface BottomSheetUiState {
    @Immutable
    data object Loading : BottomSheetUiState

    @Immutable
    data object Hidden : BottomSheetUiState

    @Immutable
    data class Error(
        val message: String,
    ) : BottomSheetUiState

    @Immutable
    data class Success(
        val imgUrl: String = "",
        ...
        val repositories: Int = 0,
        val bio: String? = "",
    ) : BottomSheetUiState
}
```

#### 📍 ViewModel에서 관리

- `DetailViewModel`에서 `BottomSheetUiState`의 상태를 업데이트하는 함수를 작성하였습니다.
    - `loadBottomSheetData()` 바텀 시트에 필요한 데이터를 반환하는 api를 호출하고, 상태를 Loading -> Success/Error로 변경합니다.
    - `createBottomSheet()` API 응답 결과에 따라 `BottomSheetUiState`를 반환하는 함수를 따로 작성하여, 코드의 가독성을 높였습니다.
    - `closeBottomSheet()` 상태를 Hidden으로 변경하는 역할을 합니다.

```Kotlin
fun loadBottomSheetData(username: String) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    if (currentState is DetailUiState.Success) {
                        currentState.copy(
                            bottomSheetUiState = BottomSheetUiState.Loading,
                        )
                    } else {
                        currentState
                    }
                }
                combine(
                    detailRepository.getRepos(username),
                    detailRepository.getUserInfo(username),
                ) { repoResult, userResult ->
                    createBottomSheet(repoResult, userResult)
                }.collect { newState ->
                    _uiState.update { state ->
                        if (state is DetailUiState.Success) {
                            state.copy(
                                bottomSheetUiState = newState,
                            )
                        } else {
                            state
                        }
                    }
                }
            }
        }

        private fun createBottomSheet(
            repoResult: Result<List<RepoResponse>>,
            userResult: Result<UserResponse>,
        ): BottomSheetUiState =
            when {
                repoResult is Result.Success && userResult is Result.Success -> {
                    val repoData = repoResult.data
                    val userData = userResult.data
                    val languageString =
                        repoData
                            .mapNotNull { it.language }
                            .toSet()
                            .joinToString(", ")

                    BottomSheetUiState.Success(
                        imgUrl = userData.avatarUrl,
                        ...
                        bio = userData.bio,
                    )
                }
               ...
            }

        fun closeBottomSheet() {
            _uiState.update { currentState ->
                if (currentState is DetailUiState.Success) {
                    currentState.copy(
                        bottomSheetUiState = BottomSheetUiState.Hidden,
                    )
                } else {
                    currentState
                }
            }
        }
```

<br>

## 💡 단위 테스트
`DetailViewModel`의 비즈니스 로직이 올바르게 동작하는지 검증하기 위해 단위 테스트를 진행하였습니다. 
- `StandardDispaatcher`를 사용해 코루틴 실행을 제어하여 안정적인 테스트 환경을 구축하였습니다.
- `Mockk`을 이용하여 Repository 의존성을 분리해 ViewModel만 테스트 가능하도록 설정하였습니다.
- `DetailUiState`의 상태 변화를 검증하여 ViewModel의 동작을 확인하였습니다.

```Kotlin
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
                    ...
                    topics = listOf("topic1", "topic2"),
                )

            // When
            coEvery {
                detailRepository.getDetail("test_user", "TestRepo")
            } returns flowOf(Result.Success(fakeDetail))

            viewModel.getDetail("test_user", "TestRepo")
            advanceUntilIdle() // 코루틴 실행

            // Then
            val expectedState =
                DetailUiState.Success(
                    imgUrl = "https://example.com/avatar.png",
                    username = "test_user",
                    ...
                    description = "test description",
                    topics = listOf("topic1", "topic2"),
                )
            assertEquals(expectedState, viewModel.uiState.value)
        }
   ...
}
```
<img width="1318" alt="image" src="https://github.com/user-attachments/assets/78fc4dc0-de87-41ea-ae05-73ee59c1284a" />

<br><br>

## 📚 사용 기술 및 라이브러리
### 📱 Android
- **Minimum SDK**: 26
- **Target SDK**: 34
- **Jetpack Compose** - 최신 UI 개발을 위한 선언형 UI 프레임워크
- **Navigation Compose** - 화면 간 네비게이션 처리
- **Coil** - 이미지 로딩 라이브러리
  
### 🛜 네트워크
- **Retrofit** - REST API 통신 라이브러리
- **Gson** - JSON 데이터를 Kotlin 객체로 변환
- **OkHttp Logging Interceptor** - 네트워크 요청 및 응답 로그 출력
  
### 🔥 의존성 주입
- **Hilt** - DI 프레임워크
  
### 💿 데이터 관리
- **Paging3** - 대량 데이터 페이징 처리

### 🔍 테스트
- **JUnit** - 단위 테스트
- **Mockk** - 테스트를 위한 가짜 객체 생성
- **kotlinx-coroutines-test** - 코루틴 테스트

<br>

## 🐱 실제 구현 화면
- 여기에 gif
