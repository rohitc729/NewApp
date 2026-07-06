package com.example.newsapp.retrofit


data class NewsResponse(
    val totalArticles: Int,
    val articles: List<NewsModel>

)

data class NewsModel(
    val content: String,
    val description: String,
    val id: String?,
    val image: String,
    val lang: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val source: Source
)

data class Source(
    val name: String,
    val url: String
)
