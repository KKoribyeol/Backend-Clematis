package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotBlank

data class TemplateRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val title: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val body: String,
)