package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.Size

data class TargetModificationRequest(

    @get:Size(min = 1, max = 255, message = "<1~255>")
    val nickname: String?,

    @get:Size(max = 12, message = "<~12>")
    val name: String?,
)