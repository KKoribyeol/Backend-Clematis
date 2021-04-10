package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.domain.RefreshToken
import com.dsm.clematis.domain.account.repository.RedisRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RedisAuthenticationService(
    private val redisRepository: RedisRepository,
) {

    fun test(): RefreshToken {
        val refreshToken = RefreshToken(
            accountEmail = "accountEmail",
            refreshToken = "refreshToken",
        )
        redisRepository.save(refreshToken)
        return redisRepository.findByIdOrNull("accountEmail") ?: throw Exception("너가 죽였어")
    }
}