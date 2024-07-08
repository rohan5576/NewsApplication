package com.rohanlodhi.newsapplication.domain.use_case

import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.di.qualifiers.IoDispatcher
import com.rohanlodhi.newsapplication.domain.model.Article
import com.rohanlodhi.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(): Resource<List<Article>> {
        return withContext(ioDispatcher) {
            repository.getNews()
        }
    }
}