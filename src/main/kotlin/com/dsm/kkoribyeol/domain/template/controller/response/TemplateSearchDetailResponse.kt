package com.dsm.kkoribyeol.domain.template.controller.response

import java.time.LocalDateTime

data class TemplateSearchDetailResponse(
    val templateId: Long?,
    val templateTitle: String,
    val templateBody: String,
    val templateCreatedAt: LocalDateTime,
    val templateUpdatedAt: LocalDateTime,
)