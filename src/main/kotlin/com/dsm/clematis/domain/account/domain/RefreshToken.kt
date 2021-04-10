package com.dsm.clematis.domain.account.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("token")
class RefreshToken(
    @Id
    val accountEmail: String,
    val refreshToken: String,
)