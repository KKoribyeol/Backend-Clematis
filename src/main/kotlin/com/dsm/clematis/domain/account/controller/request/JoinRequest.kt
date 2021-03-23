package com.dsm.clematis.domain.account.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JoinRequest(

    @JsonProperty("id")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9]{8,20}$")
    val accountId: String,

    @JsonProperty("password")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9!@#$%^&*]{8,20}$")
    val accountPassword: String,

    @JsonProperty("name")
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
    val accountName: String,
)