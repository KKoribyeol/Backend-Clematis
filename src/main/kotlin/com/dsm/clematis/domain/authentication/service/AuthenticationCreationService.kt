package com.dsm.clematis.domain.authentication.service

import com.dsm.clematis.domain.authentication.domain.RefreshToken
import com.dsm.clematis.domain.authentication.repository.RedisRepository
import com.dsm.clematis.global.attribute.Token
import com.dsm.clematis.global.security.provider.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthenticationCreationService(
    private val tokenProvider: TokenProvider,
    private val redisRepository: RedisRepository,
) {

    fun createAccessToken(accountId: String) =
        tokenProvider.createToken(
            accountId = accountId,
            tokenType = Token.ACCESS,
        )

    fun createRefreshToken(accountId: String): String {
        val refreshToken = tokenProvider.createToken(
            accountId = accountId,
            tokenType = Token.REFRESH,
        )

        saveRefreshToken(
            accountId = accountId,
            refreshToken = refreshToken,
        )

        return refreshToken
    }

    private fun saveRefreshToken(accountId: String, refreshToken: String) {
        val redisValue = RefreshToken(
            accountId = accountId,
            refreshToken = refreshToken,
        )
        redisRepository.save(redisValue)
    }
}