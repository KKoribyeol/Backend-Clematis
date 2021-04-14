package com.dsm.clematis.domain.push.external

import com.dsm.clematis.domain.project.exception.ProjectNotFoundException
import com.dsm.clematis.domain.project.repository.ProjectRepository
import com.dsm.clematis.domain.push.domain.PushNotificationHistory
import com.dsm.clematis.domain.push.domain.PushResult
import com.dsm.clematis.domain.push.external.FirebasePushNotificationMessage.FirebaseMessage
import com.dsm.clematis.domain.push.external.FirebasePushNotificationMessage.FirebaseNotification
import com.dsm.clematis.domain.push.repository.PushNotificationHistoryRepository
import com.dsm.clematis.domain.push.repository.PushResultRepository
import com.dsm.clematis.domain.push.service.PushNotificationCreationService
import com.dsm.clematis.domain.target.exception.TargetNotFoundException
import com.dsm.clematis.domain.target.repository.TargetRepository
import com.dsm.clematis.global.attribute.PushStatus
import com.google.auth.oauth2.GoogleCredentials
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FirebasePushNotificationCreationService(
    @Value("\${FCM_SECRET_KEY_PATH:fcm-secret-key-path}")
    private val fcmSecretKey: String,
    @Value("\${FCM_PROJECT_CODE:fcm-project-code")
    private val fcmProjectCode: String,

    private val firebaseConnection: FirebaseConnection,
    private val pushNotificationHistoryRepository: PushNotificationHistoryRepository,
    private val pushResultRepository: PushResultRepository,
    private val projectRepository: ProjectRepository,
    private val targetRepository: TargetRepository,
) : PushNotificationCreationService {

    private val googleCredentialApiUrl = "https://www.googleapis.com/auth/cloud-platform"
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override suspend fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        projectCode: String,
    ) {
        val history = createHistory(
            title = title,
            body = body,
            projectCode = projectCode,
            reservationDate = null,
        )

        val pushResults = targetTokens.map {
            coroutineScope.async {
                sendPushNotification(
                    targetToken = it,
                    title = title,
                    body = body,
                )
            }
        }.awaitAll()
            .map {
                PushResult(
                    history = history,
                    target = getTarget(
                        token = it!!.token,
                        projectCode = projectCode,
                    ),
                    status = if (it != null) PushStatus.SUCCESS else PushStatus.FAILURE
                )
            }

        saveAllPushNotification(pushResults)
    }

    private fun createHistory(
        title: String,
        body: String,
        projectCode: String,
        reservationDate: LocalDateTime?,
    ) = pushNotificationHistoryRepository.save(
        PushNotificationHistory(
            title = title,
            body = body,
            project = getProject(projectCode),
            reservedAt = reservationDate,
        )
    )

    private fun getProject(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode)
            ?: throw ProjectNotFoundException(projectCode)

    override fun sendPushNotification(targetToken: String, title: String, body: String) =
        firebaseConnection.sendPushNotificationToFirebase(
            uri = "/v1/projects/${fcmProjectCode}/messages:send",
            pushNotificationMessage = createPushNotificationMessage(
                targetToken = targetToken,
                title = title,
                body = body,
            ),
            authorization = "Bearer ${getFcmAccessToken()}",
        ).execute().body()

    private fun createPushNotificationMessage(targetToken: String, title: String, body: String) =
        FirebasePushNotificationMessage(
            validateOnly = false,
            message = FirebaseMessage(
                token = targetToken,
                notification = FirebaseNotification(
                    title = title,
                    body = body,
                )
            )
        )

    private fun getFcmAccessToken(): String {
        val googleCredentials = GoogleCredentials.fromStream(
            ClassPathResource(fcmSecretKey).inputStream
        ).createScoped(listOf(googleCredentialApiUrl))

        googleCredentials.refreshIfExpired()
        return googleCredentials.accessToken.tokenValue
    }

    private fun getTarget(token: String, projectCode: String) =
        targetRepository.findByProjectCodeAndToken(
            token = token,
            code = projectCode,
        ) ?: throw TargetNotFoundException(token)

    private fun saveAllPushNotification(pushResults: List<PushResult>) {
        pushResultRepository.saveAll(pushResults)
    }

    override fun sendAllPushNotification(
        targetTokens: List<String>,
        title: String,
        body: String,
        reservationDate: LocalDateTime,
        projectCode: String,
    ) {
        createHistory(
            title = title,
            body = body,
            projectCode = projectCode,
            reservationDate = reservationDate,
        )
    }
}