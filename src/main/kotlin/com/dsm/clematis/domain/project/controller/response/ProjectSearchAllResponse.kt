package com.dsm.clematis.domain.project.controller.response

data class ProjectSearchAllResponse(
    val projects: List<ProjectSearchResponse>
) {

    data class ProjectSearchResponse(
        val code: String,
        val name: String,
        val description: String?,
    )
}