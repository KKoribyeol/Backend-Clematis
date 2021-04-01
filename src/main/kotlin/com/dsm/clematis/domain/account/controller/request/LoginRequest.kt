package com.dsm.clematis.domain.account.controller.request

import javax.validation.constraints.NotBlank

data class LoginRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val accountId: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val accountPassword: String,
)