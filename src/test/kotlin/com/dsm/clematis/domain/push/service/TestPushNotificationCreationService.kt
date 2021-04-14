package com.dsm.clematis.domain.push.service

import com.dsm.clematis.domain.push.external.FirebasePushNotificationMessage
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Primary
@Service
class TestPushNotificationCreationService : PushNotificationCreationService {

    override suspend fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        projectCode: String,
    ) {}

    override fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        reservationDate: LocalDateTime,
        projectCode: String,
    ) {}

    override fun sendPushNotification(
        targetToken: String,
        title: String,
        body: String,
    ) = FirebasePushNotificationMessage.FirebaseMessage(
        token = targetToken,
        notification = FirebasePushNotificationMessage.FirebaseNotification(
            title = title,
            body = body,
        ),
    )
}