package com.example.doccur.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.Text
import com.example.doccur.repositories.NotificationRepository
import com.example.doccur.ui.screens.HomeScreen
import com.example.doccur.ui.screens.NotificationsScreen
import com.example.doccur.viewmodels.NotificationViewModel
import com.example.doccur.viewmodels.NotificationViewModelFactory

// Screen objects for navigation
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Notifications : Screen("notifications", "Notifications", Icons.Filled.Notifications)
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    repository: NotificationRepository
) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Notifications.route) {
            val viewModel: NotificationViewModel = viewModel(
                factory = NotificationViewModelFactory(repository)
            )

            val userId = 6
            val userType = "doctor"

            NotificationsScreen(
                viewModel = viewModel,
                userId = userId,
                userType = userType
            )
        }
    }
}