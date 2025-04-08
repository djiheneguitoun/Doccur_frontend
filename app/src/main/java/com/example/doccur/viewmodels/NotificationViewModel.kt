package com.example.doccur.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doccur.entities.Notification
import com.example.doccur.repositories.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchNotifications(userId: Int, userType: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getNotifications(userId, userType)
                _notifications.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load notifications: ${e.message}"
                _notifications.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun markAsRead(notificationId: Int) {
        viewModelScope.launch {
            try {
                val success = repository.markNotificationAsRead(notificationId)
                if (success) {
                    // Update the local notification list to reflect the change
                    _notifications.value = _notifications.value.map { notification ->
                        if (notification.id == notificationId) {
                            notification.copy(isRead = true)
                        } else {
                            notification
                        }
                    }

                    // You can also notify the UI that the notification has been marked as read
                    _notifications.value = _notifications.value.toList()
                }
            } catch (e: Exception) {
                _error.value = "Failed to mark notification as read: ${e.message}"
            }
        }
    }

}