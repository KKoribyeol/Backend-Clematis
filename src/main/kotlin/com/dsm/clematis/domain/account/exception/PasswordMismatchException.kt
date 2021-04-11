package com.dsm.clematis.global.exception

import org.springframework.http.HttpStatus

class PasswordMismatchException(
    password: String,
    confirmPassword: String? = null,
) : CommonException(
    code = "PASSWORD_MISMATCH",
    message = if (confirmPassword == null) "패스워드가 일치하지 않습니다. [$password]"
                else "패스워드가 일치하지 않습니다. [$password != $confirmPassword]",
    status = HttpStatus.BAD_REQUEST,
)