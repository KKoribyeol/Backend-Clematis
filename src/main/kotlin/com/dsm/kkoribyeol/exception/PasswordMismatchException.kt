package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class PasswordMismatchException(
    password: String,
) : CommonException(
    code = "PASSWORD_MISMATCH",
    message = "패스워드가 일치하지 않습니다. [password = $password]",
    status = HttpStatus.BAD_REQUEST,
)