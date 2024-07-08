package com.rohanlodhi.newsapplication.presentation.navigation

sealed class Screen(val route: String) {
    data object Headline: Screen("headline")
    data object DetailScreen: Screen("detail_screen")
}