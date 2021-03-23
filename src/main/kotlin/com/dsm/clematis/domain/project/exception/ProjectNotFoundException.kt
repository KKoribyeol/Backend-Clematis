package com.dsm.clematis.domain.project.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class ProjectNotFoundException(
    projectCode: String,
) : CommonException(
    code = "PROJECT_NOT_FOUND",
    message = "프로젝트를 찾을 수 없습니다. [$projectCode]",
    status = HttpStatus.NOT_FOUND,
)