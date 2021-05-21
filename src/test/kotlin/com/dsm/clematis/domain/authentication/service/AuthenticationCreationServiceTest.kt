//package com.dsm.clematis.domain.authentication.service
//
//import com.dsm.clematis.domain.account.domain.Account
//import com.dsm.clematis.domain.authentication.domain.RefreshToken
//import com.dsm.clematis.domain.authentication.repository.RedisRepository
//import com.dsm.clematis.global.attribute.Token
//import com.dsm.clematis.global.security.provider.TokenProvider
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//
//internal class AuthenticationCreationServiceTest {
//    private val tokenProvider = mockk<TokenProvider>()
//    private val redisRepository = mockk<RedisRepository>()
//    private val testService = AuthenticationCreationService(
//        tokenProvider = tokenProvider,
//        redisRepository = redisRepository,
//    )
//
//    private val savedAccount = Account(
//        id = "savedIdId",
//        password = "savedPassword",
//        name = "savedName",
//    )
//
//    private val accessToken = "this-is-access-token"
//    private val refreshToken = "this-is-refresh-token"
//
//    private val redisData = RefreshToken(
//        accountId = savedAccount.id,
//        refreshToken = refreshToken,
//    )
//
//    @Test
//    fun `엑세스 토큰 생성하기`() {
//        every { tokenProvider.createToken(savedAccount.id, Token.ACCESS) } returns accessToken
//
//        val accessToken = testService.createAccessToken(savedAccount.id)
//
//        assertThat(accessToken).isEqualTo(this.accessToken)
//
//        verify(exactly = 1) { tokenProvider.createToken(savedAccount.id, Token.ACCESS) }
//    }
//
//    @Test
//    fun `리프레시 토큰 생성하기`() {
//        every { tokenProvider.createToken(savedAccount.id, Token.REFRESH) } returns refreshToken
//        every { redisRepository.save(any()) } returns redisData
//
//        val refreshToken = testService.createRefreshToken(savedAccount.id)
//
//        assertThat(refreshToken).isEqualTo(this.refreshToken)
//
//        verify(exactly = 1) { tokenProvider.createToken(savedAccount.id, Token.REFRESH) }
//        verify(exactly = 1) { redisRepository.save(any()) }
//    }
//}