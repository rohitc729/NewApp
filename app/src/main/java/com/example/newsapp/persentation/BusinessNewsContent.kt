package com.example.newsapp.persentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.retrofit.NewsModel
import com.example.newsapp.utils.NewsCard
import com.example.newsapp.utils.Routs

@Composable
fun BusinessNewsContent(news:List<NewsModel>,navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(news) {
            NewsCard(it){
                navController.navigate(Routs.NewsDetailScreen(
                    title = it.title,
                    content = it.content,
                    imageUrl = it.image,
                    sourceName = it.source.name,
                    sourceUrl = it.source.url,
                    publishedAt = it.publishedAt,
                    description = it.description,
                    url = it.url
                ))
            }
        }
    }
}