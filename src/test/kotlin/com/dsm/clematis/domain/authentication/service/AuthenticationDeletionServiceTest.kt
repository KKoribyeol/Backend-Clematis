package com.dsm.clematis.domain.authentication.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.authentication.domain.RefreshToken
import com.dsm.clematis.domain.authentication.repository.RedisRepository
import io.mockk.*
import org.junit.jupiter.api.Test

internal class AuthenticationDeletionServiceTest {
    private val redisRepository = mockk<RedisRepository>()
    private val testService = AuthenticationDeletionService(
        redisRepository = redisRepository,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )

    @Test
    fun `레디스 토큰 삭제`() {
        every { redisRepository.deleteById(savedAccount.id) } just Runs

        testService.deleteToken(
            accountId = "savedIdId",
        )

        verify(exactly = 1) { redisRepository.deleteById(savedAccount.id) }
    }
}