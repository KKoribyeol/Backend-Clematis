package com.dsm.clematis.domain.project.controller.response

data class ProjectSearchAllResponse(
    val projects: List<ProjectSearchResponse>
) {

    data class ProjectSearchResponse(
        val projectCode: String,
        val projectName: String,
        val projectDescription: String?,
    )
}