package com.dsm.kkoribyeol.controller.response

import javax.validation.constraints.NotNull

data class TemplateCreationResponse(
    @get:NotNull(message = "<NULL>")
    val creationNumber: Long,
)