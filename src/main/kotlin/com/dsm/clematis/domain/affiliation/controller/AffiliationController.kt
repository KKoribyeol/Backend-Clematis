package com.dsm.clematis.domain.affiliation.controller

import com.dsm.clematis.domain.affiliation.controller.request.TargetOfGroupingRequest
import com.dsm.clematis.domain.affiliation.controller.request.TargetOfUngroupingRequest
import com.dsm.clematis.domain.affiliation.service.TargetGroupingService
import com.dsm.clematis.domain.affiliation.service.TargetUngroupingService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/affiliation")
@Validated
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
        targetToken = request.targetToken,
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

        @Pattern(regexp = "^[a-zA-Z0-9-]{1,20}$")
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestParam("group-name")
        groupName: String,

        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestParam("target-token")
        targetToken: String,
    ) = ungroupingService.ungroupTarget(
        projectCode = projectCode,
        groupName = groupName,
        targetToken = targetToken,
    )
}