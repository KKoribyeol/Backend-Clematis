package com.dsm.clematis.global.attribute

enum class Token(val millisecondOfExpirationTime: Long, val kind: String) {
    ACCESS(
        millisecondOfExpirationTime = 1000 * 60 * 60 * 4,
        kind = "accessToken"
    ),
    REFRESH(
        millisecondOfExpirationTime = 1000 * 60 * 60 * 24 * 7 * 2,
        kind = "refreshToken"
    ),
}