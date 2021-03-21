package com.dsm.kkoribyeol.controller

import com.dsm.kkoribyeol.controller.request.GroupRequest
import com.dsm.kkoribyeol.service.GroupCreationService
import com.dsm.kkoribyeol.service.GroupModificationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
@RequestMapping("/group")
@Validated
class GroupController(
    private val groupCreationService: GroupCreationService,
    private val groupModificationService: GroupModificationService,
) {

    @PostMapping
    fun createGroup(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )

        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String?,

        @Valid
        @NotNull(message = "Request Body 는 null 이 될 수 없습니다.")
        @RequestBody
        request: GroupRequest?,

    ) = groupCreationService.createGroup(
        projectCode = projectCode!!,
        groupName = request!!.groupName!!,
    )

    @PatchMapping("/{groupName}")
    fun modifyGroup(
        @Pattern(
            regexp = "[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String?,

        @Size(min = 1, max = 20, message = "<1~20>")
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @PathVariable("groupName")
        groupName: String?,

        @Valid
        @NotNull(message = "Request Body 는 null 이 될 수 없습니다.")
        @RequestBody
        request: GroupRequest?,

    ) = groupModificationService.modifyGroupName(
        projectCode = projectCode!!,
        groupName = groupName!!,
        newGroupName = request!!.groupName!!
    )
}