package com.dsm.clematis.domain.affiliation.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class AlreadyExistAffiliationException : CommonException(
    code = "ALREADY_EXIST_AFFILIATION",
    message = "이미 존재하는 소속 정보입니다.",
    status = HttpStatus.BAD_REQUEST,
)