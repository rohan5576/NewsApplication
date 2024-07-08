@file:OptIn(ExperimentalMaterial3Api::class)

package com.rohanlodhi.newsapplication.presentation.news

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rohanlodhi.newsapplication.R
import com.rohanlodhi.newsapplication.domain.model.Article
import com.rohanlodhi.newsapplication.presentation.common.ArticleItem
import com.rohanlodhi.newsapplication.presentation.navigation.Screen
import com.rohanlodhi.newsapplication.presentation.theme.NewsApplicationTheme
import com.rohanlodhi.newsapplication.presentation.theme.smallPadding
import com.rohanlodhi.newsapplication.presentation.theme.xLargePadding
import com.rohanlodhi.newsapplication.util.EmptyContent
import com.zeph7.newsapp.feature_news.util.encodeToString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is NewsUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is NewsUiEvent.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
            }
        }
    }

    NewsApplicationTheme {
        MainUI(
            uiState = viewModel.uiState,
            onEvent = viewModel::onEvent,
            snackBarHost = { SnackbarHost(hostState = snackBarHostState) },
            navigateToArticle = {
                navController.navigate(Screen.DetailScreen.route + "/${it.encodeToString()}")
            }
        )
    }
}

@Composable
private fun MainUI(
    uiState: NewsUiState,
    onEvent: (NewsUserEvent) -> Unit,
    snackBarHost: @Composable () -> Unit,
    navigateToArticle: (Article) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            modifier = Modifier.padding(vertical = smallPadding),
                            text = stringResource(R.string.top_news),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 1.dp)
                    }
                }
            )
        },
        snackbarHost = snackBarHost
    ) {
        Crossfade(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            targetState = uiState.isLoading,
            label = ""
        ) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = { CircularProgressIndicator() }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(xLargePadding),
                    verticalArrangement = Arrangement.spacedBy(xLargePadding)
                ) {
                    itemsIndexed(uiState.articles) { index, item ->
                        if (index == 0) {
                            ArticleWithImageItem(
                                article = item,
                                onClick = { navigateToArticle(item) }
                            )
                        } else {
                            ArticleItem(
                                article = item,
                                onClick = { navigateToArticle(item) }
                            )
                        }
                    }

                    if (uiState.articles.isEmpty() && uiState.message.isNotEmpty()) {
                        item {
                            EmptyContent(
                                message = uiState.message,
                                icon = R.drawable.ic_network_error,
                                onRetryClick = { onEvent(NewsUserEvent.RefreshHeadlines) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleWithImageItem(
    article: Article,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = smallPadding)
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = smallPadding)
        )
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = smallPadding)
        )
    }
}
