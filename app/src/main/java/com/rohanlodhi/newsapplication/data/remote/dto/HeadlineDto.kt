package com.rohanlodhi.newsapplication.data.remote.dto


data class HeadlineDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)