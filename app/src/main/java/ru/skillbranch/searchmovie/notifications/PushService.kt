package ru.skillbranch.searchmovie.notifications

import com.google.firebase.messaging.FirebaseMessagingService

// Сервис для уведомлений
class PushService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }
}