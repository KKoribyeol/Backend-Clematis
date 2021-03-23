package com.dsm.clematis.domain.template.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistTemplateException(
    templateTitle: String,
    templateBody: String,
) : CommonException(
    code = "ALREADY_EXIST_TEMPLATE",
    message = "이미 존재하는 템플릿입니다. [title = $templateTitle, body = $templateBody]",
    status = HttpStatus.BAD_REQUEST,
)