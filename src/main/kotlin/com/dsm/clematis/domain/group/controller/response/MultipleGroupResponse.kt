package com.dsm.clematis.domain.group.controller.response

data class MultipleGroupResponse(
    val groups: List<GroupResponse>,
) {

    data class GroupResponse(
        val name: String,
    )
}