package com.rohanlodhi.newsapplication.presentation.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohanlodhi.newsapplication.common.Resource
import com.rohanlodhi.newsapplication.domain.use_case.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NewsUiState())
        private set

    private var _uiEvent = MutableSharedFlow<NewsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getHeadlines()
    }

    fun onEvent(event: NewsUserEvent) {
        when (event) {
            NewsUserEvent.RefreshHeadlines -> getHeadlines()
        }
    }

    private fun getHeadlines() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            uiState = when (val response = getArticlesUseCase()) {
                is Resource.Loading -> {
                    uiState.copy(isLoading = true)
                }
                is Resource.Success -> {
                    uiState.copy(isLoading = false, articles = response.data!!)
                }
                is Resource.Error -> {
                    _uiEvent.emit(NewsUiEvent.ShowSnackBar(response.message!!))
                    uiState.copy(isLoading = false, message = response.message)
                }
            }
        }
    }

}