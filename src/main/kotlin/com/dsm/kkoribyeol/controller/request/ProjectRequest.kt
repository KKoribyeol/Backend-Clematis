package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ProjectRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Size(min = 1, max = 20)
    val name: String,

    @get:Size(min = 0, max = 100)
    val description: String?,
)