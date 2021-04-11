package com.dsm.clematis.domain.authentication.controller.request

import javax.validation.constraints.NotBlank

data class LogoutRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val token: String,
)