package com.dsm.kkoribyeol.global.attribute

enum class Token(val millisecondOfExpirationTime: Long) {
    ACCESS(1000 * 60 * 60 * 4),
    REFRESH(1000 * 60 * 60 * 24 * 7 * 2),
}