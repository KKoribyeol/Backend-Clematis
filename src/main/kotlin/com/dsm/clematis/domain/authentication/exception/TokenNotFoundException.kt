package com.dsm.clematis.domain.authentication.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class TokenNotFoundException(
    accountId: String,
) : CommonException(
    code = "TOKEN_NOT_FOUND",
    message = "레디스에서 리프레시 토큰을 찾을 수 없습니다. [$accountId]",
    status = HttpStatus.NOT_FOUND,
)