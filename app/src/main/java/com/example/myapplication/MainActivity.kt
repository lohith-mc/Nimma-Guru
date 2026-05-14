package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NimmaGuruViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NimmaGuruApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NimmaGuruApp(viewModel: NimmaGuruViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Wrapping the entire app in the Spiritual Background
    AppBackground {
        Scaffold(
            containerColor = Color.Transparent, // Make Scaffold transparent to show background
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            stringResource(R.string.app_name),
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge
                        ) 
                    },
                    navigationIcon = {
                        if (currentRoute != "home" && currentRoute != null) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack, 
                                    contentDescription = "Back",
                                    tint = Color(0xFFE65100) // Deep Saffron
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent, // Transparent Top Bar
                        titleContentColor = Color(0xFFE65100)
                    )
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onFindGuru = { navController.navigate("search") },
                            onCalendar = { navController.navigate("calendar") },
                            onProfile = { navController.navigate("profile") }
                        )
                    }
                    composable("search") {
                        SearchScreen(
                            viewModel = viewModel,
                            onGuruClick = { guruId -> navController.navigate("profile/$guruId") }
                        )
                    }
                    composable("calendar") {
                        CalendarScreen(viewModel = viewModel)
                    }
                    composable("profile") {
                        ProfileScreen(viewModel = viewModel)
                    }
                    composable(
                        route = "profile/{guruId}",
                        arguments = listOf(navArgument("guruId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val guruId = backStackEntry.arguments?.getString("guruId")
                        ProfileScreen(viewModel = viewModel, guruId = guruId)
                    }
                }
            }
        }
    }
}
