package com.dsm.kkoribyeol.domain.group.controller.response

data class SingleGroupResponse(
    val groupName: String,
    val targets: List<TargetInGroup>,
) {

    data class TargetInGroup(
        val targetToken: String,
        val targetNickname: String,
    )
}