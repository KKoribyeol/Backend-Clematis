package com.dsm.kkoribyeol.domain.account.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class LoginRequest(

    @JsonProperty("id")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val accountId: String,

    @JsonProperty("password")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    val accountPassword: String,
)