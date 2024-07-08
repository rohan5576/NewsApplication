package com.rohanlodhi.newsapplication.presentation.news

sealed class NewsUiEvent {
    data class ShowToast(val message: String): NewsUiEvent()
    data class ShowSnackBar(val message: String): NewsUiEvent()
}