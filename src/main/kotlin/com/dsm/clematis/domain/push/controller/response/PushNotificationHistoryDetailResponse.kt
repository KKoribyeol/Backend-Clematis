package com.dsm.clematis.domain.push.controller.response

data class PushNotificationHistoryDetailResponse(
    val pageNumber: Int,
    val totalPageNumber: Int,
    val historyDetails: List<HistoryDetail>,
    val isLastPage: Boolean,
) {

    data class HistoryDetail(
        val targetToken: String,
        val status: String,
    )
}