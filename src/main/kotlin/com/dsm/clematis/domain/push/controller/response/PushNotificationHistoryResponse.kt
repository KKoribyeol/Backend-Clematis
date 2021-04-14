package com.dsm.clematis.domain.push.controller.response

import java.time.LocalDateTime

data class PushNotificationHistoryResponse(
    val pageNumber: Int,
    val totalPageNumber: Int,
    val histories: List<History>,
    val isLastPage: Boolean,
) {

    data class History(
        val id: Long,
        val title: String,
        val body: String,
        val createdAt: LocalDateTime,
        val reservedAt: LocalDateTime?,
        val completedAt: LocalDateTime?,
    )
}