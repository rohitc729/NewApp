package com.example.newsapp.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Routs{
    @Serializable
    object MainScreen: Routs()
    @Serializable
    data class NewsDetailScreen(
        val title: String,
        val content:String,
        val imageUrl: String,
        val sourceName: String,
        val sourceUrl: String,
        val publishedAt: String,
        val description: String,
        val url: String,
    ): Routs()
}