package com.dsm.clematis.domain.push.repository

import com.dsm.clematis.domain.push.domain.PushNotificationHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PushNotificationHistoryRepository : JpaRepository<PushNotificationHistory, Int> {
    fun findByCompletedAtIsNullAndReservedAtBefore(date: LocalDateTime): List<PushNotificationHistory>
    fun findByProjectCode(projectCode: String, pageable: Pageable): Page<PushNotificationHistory>
}