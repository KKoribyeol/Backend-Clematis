package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotBlank

data class TargetUnregisterRequest(

    @get:NotBlank(message = "<NULL> <BLANK>")
    val tokens: List<String>
)