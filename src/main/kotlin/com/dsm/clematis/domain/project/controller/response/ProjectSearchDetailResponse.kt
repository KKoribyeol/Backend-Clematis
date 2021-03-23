package com.dsm.clematis.domain.project.controller.response

data class ProjectSearchDetailResponse(
    val projectCode: String,
    val projectName: String,
    val projectDescription: String?,
)