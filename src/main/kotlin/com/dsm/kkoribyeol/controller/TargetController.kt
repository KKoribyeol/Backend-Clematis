package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.TargetModificationRequest
import com.dsm.kkoribyeol.controller.request.TargetOfGroupingRequest
import com.dsm.kkoribyeol.controller.request.TargetRegistrationAllRequest
import com.dsm.kkoribyeol.controller.request.TargetUnregisterRequest
import com.dsm.kkoribyeol.controller.response.TargetSearchAllResponse
import com.dsm.kkoribyeol.controller.response.TargetSearchAllResponse.TargetSearchResponse
import com.dsm.kkoribyeol.service.TargetGroupingService
import com.dsm.kkoribyeol.service.TargetModificationService
import com.dsm.kkoribyeol.service.TargetRegistrationService
import com.dsm.kkoribyeol.service.TargetSearchService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
@RequestMapping("/target")
@Validated
class TargetController(
    private val registrationService: TargetRegistrationService,
    private val modificationService: TargetModificationService,
    private val searchService: TargetSearchService,
    private val groupingService: TargetGroupingService,
) {

    @PostMapping
    fun registerTarget(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Valid
        @RequestBody
        request: TargetRegistrationAllRequest,

    ) = registrationService.registerTarget(
        projectCode = projectCode,
        targets = request.targets
    )

    @PatchMapping("/{targetToken}")
    fun modifyTarget(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Size(min = 1, max = 255, message = "<1~255>")
        @PathVariable("targetToken")
        targetToken: String,

        @Valid
        @RequestBody
        request: TargetModificationRequest,

    ) = modificationService.modifyTarget(
        projectCode = projectCode,
        targetToken = targetToken,
        newNickname = request.nickname,
        newName = request.name,
    )

    @DeleteMapping
    fun unregisterTarget(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Valid
        @RequestBody
        request: TargetUnregisterRequest,

    ) = registrationService.unregisterTarget(
        projectCode = projectCode,
        tokens = request.tokens,
    )

    @GetMapping
    fun searchTarget(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

    ) = TargetSearchAllResponse(
        targets = searchService.searchAllTarget()
            .map {
                TargetSearchResponse(
                    token = it.token,
                    nickname = it.nickname,
                    name = it.name,
                )
            }
    )

    @GetMapping("/{targetToken}")
    fun searchTargetDetail(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Size(min = 1, max = 255, message = "<1~255>")
        @PathVariable("targetToken")
        targetToken: String,

    ): TargetSearchResponse {
        val findTarget = searchService.searchTarget(
            projectCode = projectCode,
            targetToken = targetToken,
        )

        return TargetSearchResponse(
            token = findTarget.token,
            nickname = findTarget.nickname,
            name = findTarget.name,
        )
    }

    @PostMapping("/group")
    fun groupTarget(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String,

        @Valid
        @NotNull(message = "<NULL>")
        @RequestBody
        request: TargetOfGroupingRequest,
    ) = groupingService.groupTarget(
        projectCode = projectCode,
        groupName = request.groupName,
        targetTokens = request.targetTokens,
    )
}