package com.dsm.clematis.domain.authentication.controller.request

import javax.validation.constraints.NotBlank

data class ReissueTokenRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val accessToken: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val refreshToken: String,
)