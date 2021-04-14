package com.dsm.clematis.domain.push.scheduler

import com.dsm.clematis.domain.push.service.PushNotificationCreationService
import com.dsm.clematis.domain.push.service.PushNotificationSearchService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReservationPushNotificationScheduler(
    private val pushNotificationCreationService: PushNotificationCreationService,
    private val pushNotificationSearchService: PushNotificationSearchService,
) {

    @Scheduled(cron = "0 1 * * * *")
    suspend fun sendReservationPushNotification() {
        val reservedPushNotification =
            pushNotificationSearchService.searchReservedPushNotification()

        reservedPushNotification.forEach {
            pushNotificationCreationService.sendAllPushNotification(
                targetTokens = it.pushResults.map { result -> result.target.token },
                title = it.title,
                body = it.body,
                projectCode = it.project.code,
            )
        }
    }
}