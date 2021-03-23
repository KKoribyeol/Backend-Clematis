package com.dsm.clematis.domain.affiliation.controller

import com.dsm.clematis.domain.affiliation.controller.request.TargetOfGroupingRequest
import com.dsm.clematis.domain.affiliation.controller.request.TargetOfUngroupingRequest
import com.dsm.clematis.domain.affiliation.service.TargetGroupingService
import com.dsm.clematis.domain.affiliation.service.TargetUngroupingService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/target-group")
class AffiliationController(
    private val groupingService: TargetGroupingService,
    private val ungroupingService: TargetUngroupingService,
) {

    @PostMapping
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

    @DeleteMapping
    fun ungroupTarget(
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
        request: TargetOfUngroupingRequest,

    ) = ungroupingService.ungroupTarget(
        projectCode = projectCode,
        groupName = request.groupName,
        targetToken = request.targetToken,
    )
}