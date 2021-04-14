package com.dsm.clematis.domain.push.exception

import com.dsm.clematis.global.exception.CommonException
import org.springframework.http.HttpStatus

class FirebasePushNotificationException : CommonException(
    code = "FIREBASE_PUSH_NOTIFICATION",
    message = "파이어베이스와 통신 중 문제가 발생하였습니다.",
    status = HttpStatus.INTERNAL_SERVER_ERROR,
)