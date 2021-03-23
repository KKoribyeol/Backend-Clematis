package com.dsm.kkoribyeol.domain.project.controller.request

import javax.validation.constraints.Size

data class ProjectModificationRequest(

    @get:Size(min = 1, max = 20, message = "<1~20>")
    val name: String?,

    @get:Size(min = 0, max = 100, message = "<0~100>")
    val description: String?,
)