package com.dsm.clematis.domain.group.controller.response

data class SingleGroupResponse(
    val groupName: String,
    val targets: List<TargetInGroup>,
) {

    data class TargetInGroup(
        val token: String,
        val nickname: String,
    )
}