package com.dsm.clematis.global.exception

import org.springframework.http.HttpStatus

class InvalidTokenException : CommonException(
    code = "INVALID_TOKEN",
    message = "유효하지 않은 토큰입니다.",
    status = HttpStatus.UNAUTHORIZED,
)