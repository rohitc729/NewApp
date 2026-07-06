package com.example.newsapp.persentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.retrofit.NewsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _resultState = MutableStateFlow(ResultState())
    val resultState = _resultState.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab = _selectedTab.asStateFlow()


    fun onTabChanged(index: Int) {
        if(_selectedTab.value==index) return
        _selectedTab.value = index
        loadNews(categories[index])
    }

    private val categories = listOf(
        "general",      // All News
        "business",
        "technology",
        "crime"
    )

    init {
        loadNews(categories[0])
    }

    private fun loadNews(category: String) {
        viewModelScope.launch {


            _resultState.value = _resultState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val response = newsRepository.getAllNews(category)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList<NewsModel>()
                    _resultState.value = _resultState.value.copy(
                        data = articles,
                        isLoading = false,
                        errorMessage = null
                    )

                } else {

                    _resultState.value = _resultState.value.copy(
                        data = null,
                        errorMessage = "Something went wrong",
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _resultState.value = _resultState.value.copy(
                    errorMessage = e.message,
                    isLoading = false,
                    data = null
                )
            }
        }
    }}


data class ResultState(
    val data: List<NewsModel>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)