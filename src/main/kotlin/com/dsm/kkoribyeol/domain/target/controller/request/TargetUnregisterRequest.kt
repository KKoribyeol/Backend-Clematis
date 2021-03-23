package com.dsm.kkoribyeol.domain.target.controller.request

import javax.validation.constraints.NotEmpty

data class TargetUnregisterRequest(

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    val tokens: List<String>
)