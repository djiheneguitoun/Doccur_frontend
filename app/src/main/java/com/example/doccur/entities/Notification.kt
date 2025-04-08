package com.example.doccur.entities


data class Notification(
    val id: Int,
    val message: String,
    var isRead: Boolean = false
)
