package com.dsm.clematis.domain.push.service

import com.dsm.clematis.domain.push.external.FirebasePushNotificationMessage.FirebaseMessage
import java.time.LocalDateTime

interface PushNotificationCreationService {
    suspend fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        projectCode: String,
    )
    fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        reservationDate: LocalDateTime,
        projectCode: String,
    )
    fun sendPushNotification(
        targetToken: String,
        title: String,
        body: String,
    ): FirebaseMessage?
}