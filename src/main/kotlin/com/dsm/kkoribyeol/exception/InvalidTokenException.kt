package com.dsm.kkoribyeol.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(
    val code: String = "INVALID_TOKEN",
    message: String = "잘못된 토큰입니다.",
) : AuthenticationException(message)