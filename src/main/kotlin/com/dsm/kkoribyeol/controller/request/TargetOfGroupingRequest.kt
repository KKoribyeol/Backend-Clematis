package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotEmpty

data class TargetOfGroupingRequest(

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    val targetTokens: List<String>,
)