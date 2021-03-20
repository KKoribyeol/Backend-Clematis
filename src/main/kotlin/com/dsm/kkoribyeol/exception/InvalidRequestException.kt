package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class InvalidRequestException(
    reason: String,
) : CommonException(
    code = "INVALID_REQUEST",
    message = "요청 검증에 실패하였습니다. [$reason]",
    status = HttpStatus.BAD_REQUEST,
)