package com.example.newsapp.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(drawerState: DrawerState,scope: CoroutineScope) {
    TopAppBar(
        title = {
            Text(
                "News App",
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFCB0000)
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.menu_icon),
                    contentDescription = "navigation drawer icon",
                    modifier = Modifier.rotate(180f),
                    tint=Color.White
                )
            }
        }
    )}