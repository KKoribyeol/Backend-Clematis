package com.dsm.clematis.domain.push.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.project.domain.Project
import com.dsm.clematis.domain.push.domain.PushNotificationHistory
import com.dsm.clematis.domain.push.domain.PushResult
import com.dsm.clematis.domain.push.repository.PushNotificationHistoryRepository
import com.dsm.clematis.domain.push.repository.PushResultRepository
import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.global.attribute.PushStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

internal class PushNotificationSearchServiceTest {
    private val pushNotificationHistoryRepository = mockk<PushNotificationHistoryRepository>()
    private val pushResultRepository = mockk<PushResultRepository>()
    private val testService = PushNotificationSearchService(
        pushNotificationHistoryRepository = pushNotificationHistoryRepository,
        pushResultRepository = pushResultRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val savedProject = Project(
        code = "savedProject-finally",
        name = "savedProject",
        description = "savedDescription",
        owner = savedAccount,
    )
    private val savedTarget = Target(
        token = "savedToken",
        nickname = "savedNickname",
        name = "savedName",
        project = savedProject,
    )
    private val savedPushNotificationHistory = PushNotificationHistory(
        title = "savedTitle",
        body = "savedBody",
        project = savedProject,
        reservedAt = null,
    )
    private val savedPushResult = PushResult(
        target = savedTarget,
        history = savedPushNotificationHistory,
        status = PushStatus.SUCCESS,
    )

    private val pageable = PageRequest.of(
        0,
        5,
        Sort.by("createdAt").descending()
    )

    @BeforeEach
    fun setup() {
        savedPushNotificationHistory.id = 1
        savedPushResult.id = 1
    }

    @Test
    fun `예약된 푸시 알림 히스토리 조회하기`() {
        every {
            pushNotificationHistoryRepository.findByCompletedAtIsNullAndReservedAtBefore(
                date = any()
            )
        } returns listOf(savedPushNotificationHistory)

        val pushNotificationHistories = testService.searchReservedPushNotification()

        assertThat(pushNotificationHistories)
            .map<String> { it.title }
            .containsAll(listOf(savedPushNotificationHistory.title))
        assertThat(pushNotificationHistories)
            .map<String> { it.body }
            .containsAll(listOf(savedPushNotificationHistory.body))
        assertThat(pushNotificationHistories)
            .map<String> { it.project.code }
            .containsAll(listOf(savedPushNotificationHistory.project.code))

        verify(exactly = 1) {
            pushNotificationHistoryRepository.findByCompletedAtIsNullAndReservedAtBefore(
                date = any()
            )
        }
    }

    @Test
    fun `푸시 알림 히스토리 조회하기`() {
        every {
            pushNotificationHistoryRepository.findByProjectCode(
                projectCode = savedProject.code,
                pageable = pageable,
            )
        } returns PageImpl(listOf(savedPushNotificationHistory))

        val pushNotificationHistories = testService.searchPushNotificationHistory(
            projectCode = "savedProject-finally",
            pageable = PageRequest.of(
                1, 5, Sort.by("createdAt").descending()
            ),
        )

        assertThat(pushNotificationHistories)
            .map<String> { it.title }
            .containsAll(listOf(savedPushNotificationHistory.title))
        assertThat(pushNotificationHistories)
            .map<String> { it.body }
            .containsAll(listOf(savedPushNotificationHistory.body))
        assertThat(pushNotificationHistories)
            .map<String> { it.project.code }
            .containsAll(listOf(savedPushNotificationHistory.project.code))

        verify(exactly = 1) {
            pushNotificationHistoryRepository.findByProjectCode(
                projectCode = savedProject.code,
                pageable = pageable,
            )
        }
    }

    @Test
    fun `푸시 알림 히스토리 디테일 조회하기`() {
        every {
            pushResultRepository.findByHistoryId(
                historyId = savedPushNotificationHistory.id!!,
                pageable = pageable,
            )
        } returns PageImpl(listOf(savedPushResult))

        val pushResults = testService.searchPushNotificationHistoryDetail(
            projectCode = "savedProject-finally",
            historyId = 1,
            pageable = PageRequest.of(
                1, 5, Sort.by("createdAt").descending()
            ),
        )

        assertThat(pushResults)
            .map<String> { it.target.token }
            .containsAll(listOf(savedTarget.token))
        assertThat(pushResults)
            .map<Long> { it.history.id }
            .containsAll(listOf(savedPushNotificationHistory.id))
        assertThat(pushResults)
            .map<String> { it.status.status }
            .containsAll(listOf(savedPushResult.status.status))

        verify(exactly = 1) {
            pushResultRepository.findByHistoryId(
                historyId = savedPushNotificationHistory.id!!,
                pageable = pageable,
            )
        }
    }
}