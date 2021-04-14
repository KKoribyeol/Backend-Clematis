package com.dsm.clematis.domain.push.controller

import com.dsm.clematis.domain.affiliation.service.AffiliationSearchService
import com.dsm.clematis.domain.push.controller.request.PushRequest
import com.dsm.clematis.domain.push.controller.request.TemplatePushRequest
import com.dsm.clematis.domain.push.controller.response.PushNotificationHistoryDetailResponse
import com.dsm.clematis.domain.push.controller.response.PushNotificationHistoryResponse
import com.dsm.clematis.domain.push.service.PushNotificationCreationService
import com.dsm.clematis.domain.push.service.PushNotificationSearchService
import com.dsm.clematis.domain.template.service.TemplateSearchService
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RestController
@Validated
class PushNotificationController(
    private val pushNotificationCreationService: PushNotificationCreationService,
    private val pushNotificationSearchService: PushNotificationSearchService,
    private val affiliationSearchService: AffiliationSearchService,
    private val templateSearchService: TemplateSearchService,
) {

    @PostMapping("/push")
    suspend fun push(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Valid
        @RequestBody
        request: PushRequest,
    ) = if (request.reservationDate == null)
        pushNotificationCreationService.sendAllPushNotification(
            targetTokens = getAffiliation(projectCode, request.targetGroups, request.targetTokens),
            title = request.title,
            body = request.body,
            projectCode = projectCode,
        )
    else
        pushNotificationCreationService.sendAllPushNotification(
            targetTokens = getAffiliation(projectCode, request.targetGroups, request.targetTokens),
            title = request.title,
            body = request.body,
            projectCode = projectCode,
            reservationDate = request.reservationDate
        )

    @PostMapping("/template-push")
    suspend fun templatePush(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Valid
        @RequestBody
        request: TemplatePushRequest,
    ) {
        val template = templateSearchService.searchTemplate(request.templateId, projectCode)

        if (request.reservationDate == null)
            pushNotificationCreationService.sendAllPushNotification(
                targetTokens = getAffiliation(projectCode, request.targetGroups, request.targetTokens),
                title = template.title,
                body = template.body,
                projectCode = projectCode,
            )
        else
            pushNotificationCreationService.sendAllPushNotification(
                targetTokens = getAffiliation(projectCode, request.targetGroups, request.targetTokens),
                title = template.title,
                body = template.body,
                projectCode = projectCode,
                reservationDate = request.reservationDate
            )
    }

    private fun getAffiliation(
        projectCode: String,
        targetGroups: List<String>,
        targetTokens: List<String>,
    ) = affiliationSearchService.getAffiliatedTarget(
        projectCode = projectCode,
        groupNames = targetGroups,
        targetTokens = targetTokens,
    )

    @GetMapping("/history")
    fun searchPushNotificationHistory(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
        pageable: Pageable,
    ): PushNotificationHistoryResponse {
        val histories = pushNotificationSearchService.searchPushNotificationHistory(
            projectCode = projectCode,
            pageable = pageable,
        )

        val pageNumber = histories.number
        val totalPageNumber = histories.totalPages
        val isLastPage = histories.isLast

        return PushNotificationHistoryResponse(
            pageNumber = pageNumber,
            totalPageNumber = totalPageNumber,
            histories = histories.map {
                PushNotificationHistoryResponse.History(
                    id = it.id!!,
                    title = it.title,
                    body = it.body,
                    createdAt = it.createdAt,
                    reservedAt = it.reservedAt,
                    completedAt = it.completedAt,
                )
            }.toList(),
            isLastPage = isLastPage,
        )
    }

    @GetMapping("/history/{history-id}")
    fun searchPushNotificationHistoryDetail(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,
        @NotNull(message = "<NULL>")
        @PathVariable("history-id")
        historyId: Long,
        pageable: Pageable,
    ): PushNotificationHistoryDetailResponse {
        val historyDetails = pushNotificationSearchService.searchPushNotificationHistoryDetail(
            projectCode = projectCode,
            historyId = historyId,
            pageable = pageable,
        )

        val pageNumber = historyDetails.number
        val totalPageNumber = historyDetails.totalPages
        val isLastPage = historyDetails.isLast

        return PushNotificationHistoryDetailResponse(
            pageNumber = pageNumber,
            totalPageNumber = totalPageNumber,
            historyDetails = historyDetails.map {
                PushNotificationHistoryDetailResponse.HistoryDetail(
                    targetToken = it.target.token,
                    status = it.status.status,
                )
            }.toList(),
            isLastPage = isLastPage,
        )
    }
}