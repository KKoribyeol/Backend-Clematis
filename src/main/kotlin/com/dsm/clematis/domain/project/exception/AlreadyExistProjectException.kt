package com.dsm.clematis.domain.project.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistProjectException(
    projectName: String,
) : CommonException(
    code = "ALREADY_EXIST_PROJECT",
    message = "이름이 같은 프로젝트가 이미 존재합니다. [$projectName]",
    status = HttpStatus.BAD_REQUEST,
)