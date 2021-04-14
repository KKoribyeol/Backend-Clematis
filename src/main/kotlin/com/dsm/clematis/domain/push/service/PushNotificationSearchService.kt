package com.dsm.clematis.domain.push.service

import com.dsm.clematis.domain.push.domain.PushResult
import com.dsm.clematis.domain.push.repository.PushNotificationHistoryRepository
import com.dsm.clematis.domain.push.repository.PushResultRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class PushNotificationSearchService(
    private val pushNotificationHistoryRepository: PushNotificationHistoryRepository,
    private val pushResultRepository: PushResultRepository,
) {

    fun searchReservedPushNotification() =
        pushNotificationHistoryRepository.findByCompletedAtIsNullAndReservedAtBefore(
            date = LocalDateTime.now()
        )

    fun searchPushNotificationHistory(projectCode: String, pageable: Pageable) =
        pushNotificationHistoryRepository.findByProjectCode(
            projectCode = projectCode,
            pageable = createPageRequest(pageable),
        )

    fun searchPushNotificationHistoryDetail(
        projectCode: String,
        historyId: Long,
        pageable: Pageable,
    ) = pushResultRepository.findByHistoryId(
        historyId = historyId,
        pageable = createPageRequest(pageable)
    )

    private fun createPageRequest(pageable: Pageable) =
        PageRequest.of(
            pageable.pageNumber - 1,
            pageable.pageSize,
            Sort.by("createdAt").descending()
        )
}