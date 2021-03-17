package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistAccountException(
    accountId: String,
) : CommonException(
    code = "ALREADY_EXIST_ACCOUNT",
    message = "이미 존재하는 계정입니다. [id = $accountId]",
    status = HttpStatus.BAD_REQUEST,
)