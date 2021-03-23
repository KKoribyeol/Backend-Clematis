package com.dsm.clematis.domain.template.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class TemplateNotFoundException(
    templateId: Long
) : CommonException(
    code = "TEMPLATE_NOT_FOUND",
    message = "템플릿을 찾을 수 없습니다. [templateId = $templateId]",
    status = HttpStatus.NOT_FOUND,
)