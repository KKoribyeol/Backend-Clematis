package com.dsm.clematis.domain.project.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ProjectCreationRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Size(min = 1, max = 20, message = "<1~20>")
    val name: String,

    @get:Size(min = 0, max = 100, message = "<0~100>")
    val description: String?,
)