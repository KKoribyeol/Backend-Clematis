package com.dsm.kkoribyeol.domain.group.controller.response

data class MultipleGroupResponse(
    val groups: List<GroupResponse>,
) {

    data class GroupResponse(
        val groupName: String,
    )
}