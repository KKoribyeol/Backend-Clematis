package com.dsm.clematis.domain.affiliation.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class TargetOfUngroupingRequest(

    @get:Pattern(regexp = "^[a-zA-Z0-9-]{1,20}$")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val groupName: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val targetToken: String,
)