package com.rohanlodhi.newsapplication.data.remote


import com.rohanlodhi.newsapplication.BuildConfig
import com.rohanlodhi.newsapplication.data.remote.dto.HeadlineDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String = "in",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = API_KEY,
    ): HeadlineDto


    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = BuildConfig.API_KEY
    }

}