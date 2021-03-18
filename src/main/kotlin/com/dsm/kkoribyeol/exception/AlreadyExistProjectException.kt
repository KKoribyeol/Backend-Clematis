package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistProjectException(
    projectName: String,
) : CommonException(
    code = "ALREADY_EXIST_PROJECT",
    message = "이름이 같은 프로젝트가 이미 존재합니다. [$projectName]",
    status = HttpStatus.BAD_REQUEST,
)