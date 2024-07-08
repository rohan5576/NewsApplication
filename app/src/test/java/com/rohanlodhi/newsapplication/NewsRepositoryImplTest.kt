package com.rohanlodhi.newsapplication

import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.data.remote.NewsApi
import com.rohanlodhi.newsapplication.data.remote.dto.ArticleDto
import com.rohanlodhi.newsapplication.data.remote.dto.HeadlineDto
import com.rohanlodhi.newsapplication.data.remote.dto.SourceDto
import com.rohanlodhi.newsapplication.data.repository.NewsRepositoryImpl
import com.rohanlodhi.newsapplication.domain.model.Article
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsRepositoryImplTest {

    private lateinit var repository: NewsRepositoryImpl
    private val newsApi: NewsApi = mockk()
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

    private val responseArticleDto = ArticleDto(
        source = SourceDto(
            id = "1",
            name = "name"
        ),
        author = "Author",
        title = "Title",
        description = "description",
        url = "URL",
        urlToImage = "urlToImage",
        publishedAt = "publishedAt",
        content = "content"
    )

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(newsApi)
    }

    @Test
    fun `getNews should return success when API response is successful`() = runBlocking {
        val response = mockk<HeadlineDto>()
        every { response.status } returns "ok"
        every { response.articles } returns listOf(responseArticleDto)
        coEvery { newsApi.getTopNews() } returns response


        val result = repository.getNews()
        assertNotNull(result)
        assertEquals(Resource.Success(responseArticle).message, result.message)
        coVerify { newsApi.getTopNews() }
    }

    @Test
    fun `getNews should return error when API response is unsuccessful`() = runBlocking {
        val response = mockk<HeadlineDto>()
        every { response.status } returns "error"
        every { response.articles } returns listOf(responseArticleDto)
        coEvery { newsApi.getTopNews() } returns response
        coEvery { newsApi.getTopNews() } returns response

        val result = repository.getNews()


        assertEquals(
            Resource.Error("Server error!", null).message,
            Resource.Error(result.message, null).message
        )
        coVerify { newsApi.getTopNews() }
    }
}
