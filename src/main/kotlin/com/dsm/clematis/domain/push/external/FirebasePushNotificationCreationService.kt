package com.dsm.clematis.domain.push.service

import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FirebasePushNotificationCreationService(
    @Value("\${FCM_API_KEY:fcm-api-key}")
    private val fcmApiKey: String,
    @Value("\${FCM_SECRET_KEY_PATH:fcm-secret-key-path}")
    private val fcmSecretKey: String,
) : PushNotificationCreationService {

    private val googleCredentialApiUrl = "https://www.googleapis.com/auth/cloud-platform"

    fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
    ) {

    }

    override fun sendPushNotification(targetToken: String, title: String, body: String) {

    }

    private fun createPushNotificationMessage()

    private fun getFcmAccessToken(): String {
        val googleCredentials = GoogleCredentials.fromStream(
            ClassPathResource(fcmSecretKey)
                .inputStream
        ).createScoped(listOf(googleCredentialApiUrl))

        googleCredentials.refreshIfExpired()
        return googleCredentials.accessToken.tokenValue
    }
}