package com.rohanlodhi.newsapplication.presentation.news

import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.domain.model.Article
import com.rohanlodhi.newsapplication.domain.use_case.GetArticlesUseCase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private val responseArticle: Article = Article(
        source = "String",
        author = "Author",
        title = "Title",
        description = "description",
        url = "URL",
        urlToImage = "urlToImage",
        publishedAt = "publishedAt",
        content = "content"
    )

    private lateinit var viewModel: NewsViewModel
    private val getArticlesUseCase: GetArticlesUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewsViewModel(getArticlesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `getHeadlines should update uiState with loading`() = runBlocking {
        coEvery { getArticlesUseCase() } returns Resource.Loading()
        viewModel.onEvent(NewsUserEvent.RefreshHeadlines)

        assertEquals(true, viewModel.uiState.isLoading)
    }

    @Test
    fun `getHeadlines should update uiState with articles on success`() = runBlocking {
        val articles = listOf(responseArticle)
        coEvery { getArticlesUseCase() } returns Resource.Success(articles)


        viewModel.onEvent(NewsUserEvent.RefreshHeadlines)

        assertEquals(false, viewModel.uiState.isLoading)
        assertEquals(articles, viewModel.uiState.articles)
    }
}
