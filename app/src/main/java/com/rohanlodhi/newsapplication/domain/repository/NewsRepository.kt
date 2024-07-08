package com.rohanlodhi.newsapplication.domain.repository

import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.domain.model.Article

interface NewsRepository {

    suspend fun getNews(): Resource<List<Article>>
}