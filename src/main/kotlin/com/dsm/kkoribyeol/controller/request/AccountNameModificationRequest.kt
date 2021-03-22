package com.dsm.kkoribyeol.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class AccountNameModificationRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
    val newName: String,
)