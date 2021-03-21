package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.GroupRequest
import com.dsm.kkoribyeol.service.GroupCreationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@RestController
@RequestMapping("/group")
@Validated
class GroupController(
    private val groupCreationService: GroupCreationService,
) {

    @PostMapping
    fun createGroup(
        @Size(min = 9, max = 28, message = "<9~28>")
        @RequestHeader("projectCode")
        projectCode: String,
        @Valid
        @NotNull(message = "Request Body 는 null 이 될 수 없습니다.")
        @RequestBody
        request: GroupRequest?,
    ) = groupCreationService.createGroup(
        projectCode = projectCode,
        groupName = request!!.groupName!!,
    )
}