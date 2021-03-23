package com.dsm.clematis.domain.template.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TemplateSearchAllResponse(
    val templates: List<TemplateSearchResponse>,
) {

    data class TemplateSearchResponse(
        @JsonProperty("id")
        val templateId: Long,
        @JsonProperty("title")
        val templateTitle: String,
        @JsonProperty("body")
        val templateBody: String,
    )
}