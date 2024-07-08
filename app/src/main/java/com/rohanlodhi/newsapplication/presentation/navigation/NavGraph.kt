package com.rohanlodhi.newsapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.rohanlodhi.newsapplication.domain.model.Article
import com.rohanlodhi.newsapplication.presentation.detailscreen.DetailScreen
import com.rohanlodhi.newsapplication.presentation.news.NewsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Headline.route
    ) {
        composable(route = Screen.Headline.route) {
            NewsScreen(navController, hiltViewModel())
        }
        composable(
            route = Screen.DetailScreen.route + "/{article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            val article = Gson().fromJson(articleJson, Article::class.java)
            DetailScreen(navController = navController, newsItem = article)
        }

    }
}