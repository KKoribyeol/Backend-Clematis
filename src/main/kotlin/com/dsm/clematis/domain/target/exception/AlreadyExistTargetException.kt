package com.dsm.clematis.domain.target.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistTargetException : CommonException(
    code = "ALREADY_EXIST_TARGET",
    message = "이미 존재하는 타겟입니다.",
    status = HttpStatus.BAD_REQUEST,
)