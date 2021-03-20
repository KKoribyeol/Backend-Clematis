package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistTargetException : CommonException(
    code = "ALREADY_EXIST_TARGET",
    message = "이미 존재하는 타겟입니다.",
    status = HttpStatus.BAD_REQUEST,
)