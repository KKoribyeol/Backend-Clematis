package com.dsm.clematis.global.exception

import org.springframework.http.HttpStatus

class NonExistPushStatusException(
    pushStatus: String,
) : CommonException(
    code = "NON_EXIST_STATUS",
    message = "존재하지 않는 푸시 상태입니다. [$pushStatus]",
    status = HttpStatus.BAD_REQUEST,
)