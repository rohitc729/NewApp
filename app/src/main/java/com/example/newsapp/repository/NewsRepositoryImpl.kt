package com.example.newsapp.repository

import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.retrofit.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
): NewsRepository {
    override suspend fun getAllNews(category:String): Response<NewsResponse> {
        return newsApi.getNews(
            category = category,
            apiKey = ""
        )
    }
}