package com.example.newsapp.persentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.utils.MyTopAppBar
import com.example.newsapp.utils.Routs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.net.toUri


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                NavHost(startDestination = Routs.MainScreen, navController = navController) {
                    composable<Routs.MainScreen> {
                        App(navController = navController)
                    }
                    composable<Routs.NewsDetailScreen> {
                        val data = it.toRoute<Routs.NewsDetailScreen>()
                        NewsDetailScreen(
                            navController = navController,
                            title = data.title,
                            content = data.content,
                            imageUrl = data.imageUrl,
                            sourceName = data.sourceName,
                            sourceUrl = data.sourceUrl,
                            publishedAt = data.publishedAt,
                            description = data.description,
                            url = data.url,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController) {

    val viewModel: MainScreenViewModel = hiltViewModel()
    val newsState = viewModel.resultState.collectAsStateWithLifecycle().value

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val url ="https://github.com/rohitc729/NewApp"
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    val context = LocalContext.current

    val tabs = listOf(
        "All",
        "Business",
        "Tech",
        "Crime"
    )
    var selectedTab by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.img),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                    Text("News App", fontSize = 24.sp)
                }
                NavigationDrawerItem(
                    label = { Text("DashBoard") },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.home),
                            contentDescription = "navigation drawer icon",
                        )
                    },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Github") },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.github),
                            contentDescription = "navigation drawer icon",
                        )
                    },
                    selected = false,
                    onClick = {
                        context.startActivity(intent)
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = {
                MyTopAppBar(
                    drawerState = drawerState,
                    scope = scope
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                PrimaryScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 0.dp,
                    containerColor = Color(0xFFCB0000)
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                viewModel.onTabChanged(selectedTab)
                            },
                            text = { Text(title, color = Color.White) }
                        )
                    }
                }
                when {
                    newsState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    newsState.errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(newsState.errorMessage)
                        }
                    }

                    newsState.data != null -> {
                        when (selectedTab) {
                            0 -> AllNewsContent(newsState.data, navController = navController)
                            1 -> BusinessNewsContent(newsState.data, navController = navController)
                            2 -> TechNewsContent(newsState.data, navController = navController)
                            3 -> CrimeNewsContent(newsState.data, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

