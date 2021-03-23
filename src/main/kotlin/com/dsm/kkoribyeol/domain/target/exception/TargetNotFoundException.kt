package com.dsm.kkoribyeol.domain.target.exception

import com.dsm.kkoribyeol.global.exception.CommonException
import org.springframework.http.HttpStatus

class TargetNotFoundException(
    targetToken: String,
) : CommonException(
    code = "TARGET_NOT_FOUND",
    message = "타겟을 찾을 수 없습니다. [$targetToken]",
    status = HttpStatus.NOT_FOUND,
)