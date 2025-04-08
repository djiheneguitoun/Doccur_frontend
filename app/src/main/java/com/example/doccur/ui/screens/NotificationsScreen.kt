package com.example.doccur.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doccur.entities.Notification
import com.example.doccur.viewmodels.NotificationViewModel

@Composable
fun NotificationsScreen(
    viewModel: NotificationViewModel,
    userId: Int,
    userType: String
) {
    // Collect state from ViewModel
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Effect to fetch notifications when the screen is displayed
    LaunchedEffect(key1 = userId, key2 = userType) {
        viewModel.fetchNotifications(userId, userType)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Show loading spinner if loading
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Show error if there's an error
        error?.let { errorMsg ->
            Text(
                text = errorMsg,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }

        // Show notifications list
        if (!isLoading && error == null) {
            if (notifications.isEmpty()) {
                // Show empty state
                Text(
                    text = "No notifications",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Show notifications list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(notifications) { notification ->
                        NotificationItem(
                            notification = notification,
                            onMarkAsRead = { viewModel.markAsRead(notification.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = notification.message?: "No message available",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(8.dp))

            // Show Mark as Read button only if notification is not read yet
            if (!notification.isRead) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onMarkAsRead,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text("Mark as Read")
                    }
                }
            }
        }
    }
}