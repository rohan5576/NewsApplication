package com.rohanlodhi.newsapplication.data.mapper


import com.rohanlodhi.newsapplication.data.remote.dto.ArticleDto
import com.rohanlodhi.newsapplication.domain.model.Article

fun ArticleDto.toArticle(): Article {
    return Article(
        source = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}
