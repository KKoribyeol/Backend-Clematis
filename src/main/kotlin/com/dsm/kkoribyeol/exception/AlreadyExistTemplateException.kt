package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistTemplateException(
    templateTitle: String,
    templateBody: String,
) : CommonException(
    code = "ALREADY_EXIST_TEMPLATE",
    message = "이미 존재하는 템플릿입니다. [title = $templateTitle, body = $templateBody]",
    status = HttpStatus.BAD_REQUEST,
)