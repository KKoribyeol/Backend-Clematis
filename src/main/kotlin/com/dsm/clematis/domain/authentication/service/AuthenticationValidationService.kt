package com.dsm.clematis.domain.authentication.service

import com.dsm.clematis.domain.authentication.exception.TokenNotFoundException
//import com.dsm.clematis.domain.authentication.repository.RedisRepository
import com.dsm.clematis.global.attribute.Token
import com.dsm.clematis.global.exception.InvalidTokenException
import com.dsm.clematis.global.security.provider.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthenticationValidationService(
//    private val redisRepository: RedisRepository,
    private val tokenProvider: TokenProvider,
) {

    fun validateBothToken(accessToken: String, refreshToken: String) {
        val isValidAccessToken = validateToken(accessToken, Token.ACCESS)
        val isValidRefreshToken = validateToken(refreshToken, Token.REFRESH)

        if (!(isValidAccessToken && isValidRefreshToken))
            throw InvalidTokenException()
    }

    fun validateToken(token: String, tokenType: Token): Boolean {
        val isValidToken = tokenProvider.isToken(
            token = token,
            tokenType = tokenType
        )
//                && if (tokenType == Token.REFRESH)
//            isExistRefreshTokenInRedis(
//                refreshToken = token
//            ) else true

        if (!isValidToken)
            throw InvalidTokenException()

        return isValidToken
    }

//    private fun isExistRefreshTokenInRedis(refreshToken: String): Boolean {
//        val accountId = tokenProvider.getData(refreshToken)
//        val redisValue = redisRepository.findByIdOrNull(accountId)
//            ?: throw TokenNotFoundException(accountId)
//
//        return redisValue.refreshToken == refreshToken
//    }
}