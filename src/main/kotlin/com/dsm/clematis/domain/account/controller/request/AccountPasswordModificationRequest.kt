package com.dsm.clematis.domain.account.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class AccountPasswordModificationRequest(

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9!@#$%^&*]{8,20}$")
    val newPassword: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "정규표현식 = ^[a-zA-Z0-9!@#$%^&*]{8,20}$")
    val confirmNewPassword: String,
)