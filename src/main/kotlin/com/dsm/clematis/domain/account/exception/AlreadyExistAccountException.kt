package com.dsm.clematis.domain.account.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistAccountException(
    accountId: String,
) : CommonException(
    code = "ALREADY_EXIST_ACCOUNT",
    message = "이미 존재하는 계정입니다. [$accountId]",
    status = HttpStatus.BAD_REQUEST,
)