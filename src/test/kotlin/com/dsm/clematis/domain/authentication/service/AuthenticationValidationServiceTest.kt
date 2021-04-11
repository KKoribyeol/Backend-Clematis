package com.dsm.clematis.domain.authentication.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.authentication.domain.RefreshToken
import com.dsm.clematis.domain.authentication.repository.RedisRepository
import com.dsm.clematis.global.attribute.Token
import com.dsm.clematis.global.exception.CommonException
import com.dsm.clematis.global.exception.InvalidTokenException
import com.dsm.clematis.global.security.provider.TokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class AuthenticationValidationServiceTest {
    private val tokenProvider = mockk<TokenProvider>()
    private val redisRepository = mockk<RedisRepository>()
    private val testService = AuthenticationValidationService(
        tokenProvider = tokenProvider,
        redisRepository = redisRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )

    private val accessToken = "this-is-access-token"
    private val refreshToken = "this-is-refresh-token"

    private val redisData = RefreshToken(
        accountId = savedAccount.id,
        refreshToken = refreshToken,
    )

    @Test
    fun `엑세스 토큰 검증하기`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        val isValidToken = testService.validateToken(
            token = "this-is-access-token",
            tokenType = Token.ACCESS,
        )

        assertThat(isValidToken).isTrue

        verify(exactly = 1) { tokenProvider.isToken(accessToken, Token.ACCESS) }
        verify(exactly = 0) { tokenProvider.getData(any()) }
        verify(exactly = 0) { redisRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `리프레시 토큰 검증하기`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        val isValidToken = testService.validateToken(
            token = "this-is-refresh-token",
            tokenType = Token.REFRESH,
        )

        assertThat(isValidToken).isTrue

        verify(exactly = 1) { tokenProvider.isToken(refreshToken, Token.REFRESH) }
        verify(exactly = 1) { tokenProvider.getData(refreshToken) }
        verify(exactly = 1) { redisRepository.findByIdOrNull(savedAccount.id) }
    }

    @Test
    fun `엑세스 토큰 검증 실패 - throw InvalidTokenException`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        assertThrows<InvalidTokenException> {
            testService.validateToken(
                token = "this-is-access-token",
                tokenType = Token.REFRESH,
            )
        }

        verify(exactly = 1) { tokenProvider.isToken(accessToken, Token.REFRESH) }
        verify(exactly = 0) { tokenProvider.getData(any()) }
        verify(exactly = 0) { redisRepository.findByIdOrNull(any()) }
    }

    @Test
    fun `리프레시 토큰 검증 실패`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        assertThrows<InvalidTokenException> {
            testService.validateToken(
                token = "this-is-refresh-token",
                tokenType = Token.ACCESS,
            )
        }

        verify(exactly = 1) { tokenProvider.isToken(refreshToken, Token.ACCESS) }
        verify(exactly = 0) { tokenProvider.getData(refreshToken) }
        verify(exactly = 0) { redisRepository.findByIdOrNull(savedAccount.id) }
    }

    @Test
    fun `엑세스, 리프레시 모두 검증하기`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        testService.validateBothToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )

        verify(exactly = 1) { tokenProvider.isToken(accessToken, Token.ACCESS) }
        verify(exactly = 1) { tokenProvider.isToken(refreshToken, Token.REFRESH) }
        verify(exactly = 1) { tokenProvider.getData(refreshToken) }
        verify(exactly = 1) { redisRepository.findByIdOrNull(savedAccount.id) }
    }

    @Test
    fun `엑세스, 리프레시 검증 실패`() {
        every { tokenProvider.isToken(accessToken, Token.ACCESS) } returns true
        every { tokenProvider.isToken(refreshToken, Token.REFRESH) } returns true
        every { tokenProvider.isToken(accessToken, Token.REFRESH) } returns false
        every { tokenProvider.isToken(refreshToken, Token.ACCESS) } returns false

        every { tokenProvider.getData(accessToken) } returns savedAccount.id
        every { tokenProvider.getData(refreshToken) } returns savedAccount.id

        every { redisRepository.findByIdOrNull(savedAccount.id) } returns redisData

        assertThrows<InvalidTokenException> {
            testService.validateBothToken(
                accessToken = refreshToken,
                refreshToken = accessToken,
            )
        }

        verify(exactly = 0) { tokenProvider.isToken(accessToken, Token.REFRESH) }
        verify(exactly = 1) { tokenProvider.isToken(refreshToken, Token.ACCESS) }
        verify(exactly = 0) { tokenProvider.getData(refreshToken) }
        verify(exactly = 0) { redisRepository.findByIdOrNull(savedAccount.id) }
    }
}