package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotEmpty

data class TargetUnregisterRequest(

    @get:NotEmpty(message = "<NULL> <EMPTY>")
    val tokens: List<String>
)