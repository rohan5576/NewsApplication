package com.rohanlodhi.newsapplication.data.repository

import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.data.mapper.toArticle
import com.rohanlodhi.newsapplication.data.remote.NewsApi
import com.rohanlodhi.newsapplication.domain.model.Article
import com.rohanlodhi.newsapplication.domain.repository.NewsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getNews(): Resource<List<Article>> {
        return try {
            val response = newsApi.getTopNews()
            if (response.status == "ok") Resource.Success(response.articles.map { it.toArticle() })
            else Resource.Error("Server error!")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong!")
        } catch (e: IOException) {
            Resource.Error("No Internet Connection!")
        }
    }
}