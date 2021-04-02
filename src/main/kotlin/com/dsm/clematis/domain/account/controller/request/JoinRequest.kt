package com.dsm.clematis.domain.account.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class JoinRequest(

    @get:NotBlank(message = "아이디를 작성해주세요.")
    @get:Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "아이디는 ^[a-zA-Z0-9]{8,20}$ 에 맞아야 합니다.")
    val accountId: String,

    @get:NotBlank(message = "비밀번호를 작성해주세요.")
    @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,20}$", message = "비밀번호는 ^[a-zA-Z0-9!@#$%^&*]{8,20}$ 에 맞아야 합니다.")
    val accountPassword: String,

    @get:NotBlank(message = "이름을 작성해주세요.")
    @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "이름은 ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$ 에 맞아야 합니다.")
    val accountName: String,
)