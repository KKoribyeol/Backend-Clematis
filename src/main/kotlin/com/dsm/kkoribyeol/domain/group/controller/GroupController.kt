package com.dsm.kkoribyeol.domain.group.controller

import com.dsm.kkoribyeol.domain.group.controller.request.GroupRequest
import com.dsm.kkoribyeol.domain.group.controller.response.MultipleGroupResponse
import com.dsm.kkoribyeol.domain.group.controller.response.MultipleGroupResponse.GroupResponse
import com.dsm.kkoribyeol.domain.group.controller.response.SingleGroupResponse
import com.dsm.kkoribyeol.domain.group.controller.response.SingleGroupResponse.TargetInGroup
import com.dsm.kkoribyeol.domain.group.service.GroupCreationService
import com.dsm.kkoribyeol.domain.group.service.GroupDeletionService
import com.dsm.kkoribyeol.domain.group.service.GroupModificationService
import com.dsm.kkoribyeol.domain.group.service.GroupSearchService
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
    private val groupDeletionService: GroupDeletionService,
    private val groupSearchService: GroupSearchService,
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

    @DeleteMapping("/{groupName}")
    fun deleteGroup(
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

    ) = groupDeletionService.deleteGroup(
        projectCode = projectCode!!,
        groupName = groupName!!,
    )

    @GetMapping("/{groupName}")
    fun singleSearchGroup(
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

    ) = SingleGroupResponse(
        groupName = groupName!!,
        targets = groupSearchService.searchTargetInGroup(
            projectCode = projectCode!!,
            groupName = groupName,
        ).map {
            TargetInGroup(
                targetToken = it.target.token,
                targetNickname = it.target.nickname,
            )
        }
    )

    @GetMapping
    fun multipleSearchGroup(
        @Pattern(
            regexp = "^[a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}$",
            message = "정규표현식: [a-zA-Z0-9]{1,20}-[a-zA-Z0-9]{7}"
        )
        @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
        @RequestHeader("projectCode")
        projectCode: String?,

    ) = MultipleGroupResponse(
        groups = groupSearchService.searchAllGroup(
            projectCode = projectCode!!,
        ).map {
            GroupResponse(
               groupName = it.groupName
            )
        }
    )
}