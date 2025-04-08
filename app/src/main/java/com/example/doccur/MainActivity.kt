package com.example.doccur

// In com.example.doccur/MainActivity.kt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.doccur.api.RetrofitClient
import com.example.doccur.repositories.NotificationRepository
import com.example.doccur.ui.screens.NotificationsScreen
import com.example.doccur.ui.theme.DoccurTheme
import com.example.doccur.viewmodels.NotificationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create repository and view model factory
        val repository = NotificationRepository(RetrofitClient.apiService)

        setContent {
            DoccurTheme{
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(repository)
                }
            }
        }
    }
}

@Composable
fun MainScreen(repository: NotificationRepository) {
    val navController = rememberNavController()

    // Define navigation items
    val items = listOf(
        Screen.Home,
        Screen.Notifications
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {  },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    // Your home screen composable goes here
                    Text("Home Screen")
                }
                composable(Screen.Notifications.route) {
                    // Create view model for notifications screen
                    val viewModel: NotificationViewModel = viewModel(
                        factory = NotificationViewModelFactory(repository)
                    )

                    // For demo purposes, we're using hard-coded values
                    // In a real app, you would get these from user session
                    val userId = 3
                    val userType = "patient" // or "doctor"

                    NotificationsScreen(
                        viewModel = viewModel,
                        userId = userId,
                        userType = userType
                    )
                }
            }
        }
    }
}

// Screen objects for navigation
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Notifications : Screen("notifications", "Notifications", Icons.Filled.Notifications)
}

// Create a ViewModelFactory for NotificationViewModel
class NotificationViewModelFactory(
    private val repository: NotificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}