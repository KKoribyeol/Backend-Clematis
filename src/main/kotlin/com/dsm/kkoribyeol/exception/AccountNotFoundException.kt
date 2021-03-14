package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AccountNotFoundException(
    id: String,
) : CommonException(
    code = "ACCOUNT_NOT_FOUND",
    message = "계정을 찾을 수 없습니다. [id = $id]",
    status = HttpStatus.NOT_FOUND,
)