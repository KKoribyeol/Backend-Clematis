package com.dsm.clematis.domain.template.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TemplateRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Size(min = 1, max = 40, message = "<1~40>")
    val title: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Size(min = 1, max = 255, message = "<1~255>")
    val body: String,
)