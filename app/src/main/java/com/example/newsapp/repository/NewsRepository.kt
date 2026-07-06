package com.example.newsapp.repository

import com.example.newsapp.retrofit.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getAllNews(category:String): Response<NewsResponse>
}