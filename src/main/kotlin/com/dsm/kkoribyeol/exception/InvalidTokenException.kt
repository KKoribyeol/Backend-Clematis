package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class InvalidTokenException : CommonException(
    code = "INVALID_TOKEN",
    message = "토큰이 잘못되었거나 만료되었습니다.",
    status = HttpStatus.UNAUTHORIZED,
)