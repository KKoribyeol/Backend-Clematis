package com.dsm.clematis.domain.account.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JoinRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9]{8,20}$")
    val accountId: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9!@#$%^&*]{8,20}$")
    val accountPassword: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
    val accountName: String,
)