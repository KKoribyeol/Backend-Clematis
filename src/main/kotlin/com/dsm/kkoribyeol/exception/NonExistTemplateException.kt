package com.dsm.kkoribyeol.exception

import com.dsm.kkoribyeol.exception.handler.CommonException
import org.springframework.http.HttpStatus

class NonExistTemplateException(
    templateId: Long
) : CommonException(
    code = "NON_EXIST_TEMPLATE",
    message = "존재하지 않는 템플릿입니다. [templateId = $templateId]",
    status = HttpStatus.BAD_REQUEST,
)