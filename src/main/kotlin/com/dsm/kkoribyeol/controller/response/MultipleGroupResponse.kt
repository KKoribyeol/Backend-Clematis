package com.dsm.kkoribyeol.controller.response

data class MultipleGroupResponse(
    val groups: List<GroupResponse>,
) {

    data class GroupResponse(
        val groupName: String,
    )
}