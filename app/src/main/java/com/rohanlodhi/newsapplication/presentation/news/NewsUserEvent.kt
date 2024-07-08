package com.rohanlodhi.newsapplication.presentation.news

sealed class NewsUserEvent {
    data object RefreshHeadlines: NewsUserEvent()
}