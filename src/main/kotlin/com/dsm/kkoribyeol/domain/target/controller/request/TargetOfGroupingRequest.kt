package com.dsm.kkoribyeol.domain.target.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class TargetOfGroupingRequest(

    @Pattern(regexp = "^[a-zA-Z0-9-]{1,20}$")
    @NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val groupName: String,

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    val targetTokens: List<String>,
)