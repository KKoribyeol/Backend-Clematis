package com.dsm.clematis.domain.authentication.controller.request

import javax.validation.constraints.NotBlank

data class LoginRequest(

    @get:NotBlank(message = "아이디를 작성해주세요.")
    val accountId: String,

    @get:NotBlank(message = "비밀번호를 작성해주세요.")
    val accountPassword: String,
)