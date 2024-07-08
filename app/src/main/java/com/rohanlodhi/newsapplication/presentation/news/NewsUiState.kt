package com.rohanlodhi.newsapplication.presentation.news

import com.rohanlodhi.newsapplication.domain.model.Article

data class NewsUiState(
    val isLoading: Boolean = false,
    val message: String = "",
    val articles: List<Article> = listOf()
)