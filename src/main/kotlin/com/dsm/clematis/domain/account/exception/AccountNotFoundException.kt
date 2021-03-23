package com.dsm.clematis.domain.account.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AccountNotFoundException(
    accountId: String,
) : CommonException(
    code = "ACCOUNT_NOT_FOUND",
    message = "계정을 찾을 수 없습니다. [id = $accountId]",
    status = HttpStatus.NOT_FOUND,
)