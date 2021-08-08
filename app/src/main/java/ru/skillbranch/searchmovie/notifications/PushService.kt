package ru.skillbranch.searchmovie.notifications

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Сервис для уведомлений
class PushService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        // sendRegistrationToServer(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Если есть полезная нагрузка
        if (remoteMessage.data.isNotEmpty()) {
            // Обрабатываем(просто кладем в extras)
            val intent = Intent(INTENT_FILTER)
            remoteMessage.data.forEach {
                    entry -> intent.putExtra(entry.key, entry.value)
            }

            // И отправляем броадкаст
            sendBroadcast(intent)
        }
    }

    companion object {
        const val INTENT_FILTER = "PUSH_EVENT"
        const val KEY_ACTION = "action"
        const val KEY_MESSAGE = "message"

        const val ACTION_SHOW_MESSAGE = "show_message"
    }
}