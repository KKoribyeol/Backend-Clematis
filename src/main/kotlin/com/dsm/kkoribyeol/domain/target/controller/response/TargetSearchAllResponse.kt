package com.dsm.kkoribyeol.domain.target.controller.response

data class TargetSearchAllResponse(
    val targets: List<TargetSearchResponse>,
) {

    data class TargetSearchResponse(
        val token: String,
        val nickname: String,
        val name: String?,
    )
}