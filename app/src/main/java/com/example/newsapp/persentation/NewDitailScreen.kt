package com.example.newsapp.persentation

import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil3.Uri
import coil3.compose.AsyncImage

@Composable
fun NewsDetailScreen(
    navController: NavHostController,
    title: String,
    content: String,
    imageUrl: String,
    sourceName: String,
    sourceUrl: String,
     publishedAt: String,
     description: String,
    url: String
) {
    val context = LocalContext.current
   Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Text(title, fontWeight = FontWeight.W500)
            Text(content, fontWeight = FontWeight.W300)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
            ) {
                Text("Published At: ")
                Text(publishedAt, fontWeight = FontWeight.W300,)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                Text("Source Name:")
                Text(sourceName, fontWeight = FontWeight.W300)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Source url:")
                Text(
                    sourceUrl,
                    fontWeight = FontWeight.W300,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        val intent  = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )
            }

        }
    }
}