package com.example.newsapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("lang") language: String = "en",
        @Query("country") country: String = "in",
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String
    ): Response<NewsResponse>
}