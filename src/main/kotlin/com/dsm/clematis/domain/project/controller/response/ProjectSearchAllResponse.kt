package com.dsm.clematis.domain.project.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ProjectSearchAllResponse(
    val projects: List<ProjectSearchResponse>
) {

    data class ProjectSearchResponse(
        @JsonProperty("code")
        val projectCode: String,
        @JsonProperty("name")
        val projectName: String,
    )
}