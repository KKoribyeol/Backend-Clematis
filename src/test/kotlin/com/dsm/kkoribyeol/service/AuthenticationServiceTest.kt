package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.exception.AlreadyExistAccountException
import com.dsm.kkoribyeol.exception.PasswordMismatchException
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.service.attribute.Token
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import com.dsm.kkoribyeol.service.provider.TokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

internal class AuthenticationServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val tokenProvider = mockk<TokenProvider>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val testService = AuthenticationService(
        accountRepository = accountRepository,
        tokenProvider = tokenProvider,
        passwordEncoder = passwordEncoder,
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val nonExistAccount = Account(
        id = "idIdIdId",
        password = "password",
        name = "nameName",
    )
    private val invalidPasswordAccount = Account(
        id = "savedIdId",
        password = "invalidPassword",
        name = "savedName",
    )

    private val accessToken = "this-is-access-token"
    private val refreshToken = "this-is-refresh-token"

    @Test
    fun `계정 생성하기`() {
        every { accountRepository.existsById(savedAccount.id) } returns true
        every { accountRepository.existsById(nonExistAccount.id) } returns false
        every { accountRepository.save(any()) } returns nonExistAccount
        every { passwordEncoder.encode(savedAccount.password) } returns savedAccount.password
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        val account = testService.createAccount(
            accountId = "idIdIdId",
            accountPassword = "password",
            accountName = "nameName",
        )

        assertThat(account.id).isEqualTo(nonExistAccount.id)
        assertThat(account.password).isEqualTo(nonExistAccount.password)
        assertThat(account.name).isEqualTo(nonExistAccount.name)

        verify(exactly = 1) { accountRepository.existsById(nonExistAccount.id) }
        verify(exactly = 1) { accountRepository.save(any()) }
        verify(exactly = 1) { passwordEncoder.encode(nonExistAccount.password) }
    }

    @Test
    fun `계정 생성하기 - throw AlreadyExistAccountException`() {
        every { accountRepository.existsById(savedAccount.id) } returns true
        every { accountRepository.existsById(nonExistAccount.id) } returns false
        every { accountRepository.save(any()) } returns nonExistAccount
        every { passwordEncoder.encode(savedAccount.password) } returns savedAccount.password
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        assertThrows<AlreadyExistAccountException> {
            testService.createAccount(
                accountId = "savedIdId",
                accountPassword = "savedPassword",
                accountName = "savedName",
            )
        }

        verify(exactly = 1) { accountRepository.existsById(savedAccount.id) }
        verify(exactly = 0) { accountRepository.save(any()) }
        verify(exactly = 0) { passwordEncoder.encode(nonExistAccount.password) }
    }
    
    @Test
    fun `계정 검증하기`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { accountRepository.findByIdOrNull(invalidPasswordAccount.id) } returns savedAccount
        every { passwordEncoder.matches(savedAccount.password, savedAccount.password) } returns true
        every { passwordEncoder.matches(invalidPasswordAccount.password, savedAccount.password) } returns false

        val isValidate = testService.validateAccount(
            accountId = "savedIdId",
            accountPassword = "savedPassword",
        )

        assertThat(isValidate).isTrue

        verify(exactly = 1) { accountRepository.findByIdOrNull(savedAccount.id) }
        verify(exactly = 1) { passwordEncoder.matches(savedAccount.password, savedAccount.password) }
    }

    @Test
    fun `계정 검증하기 - throw AccountNotFoundException`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { accountRepository.findByIdOrNull(invalidPasswordAccount.id) } returns savedAccount
        every { passwordEncoder.matches(savedAccount.password, savedAccount.password) } returns true
        every { passwordEncoder.matches(invalidPasswordAccount.password, savedAccount.password) } returns false

        assertThrows<AccountNotFoundException> {
            testService.validateAccount(
                accountId = "idIdIdId",
                accountPassword = "password",
            )
        }

        verify(exactly = 1) { accountRepository.findByIdOrNull(nonExistAccount.id) }
        verify(exactly = 0) { passwordEncoder.matches(any(), any()) }
    }

    @Test
    fun `계정 검증하기 - throw PasswordMismatchException`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { accountRepository.findByIdOrNull(invalidPasswordAccount.id) } returns savedAccount
        every { passwordEncoder.matches(savedAccount.password, savedAccount.password) } returns true
        every { passwordEncoder.matches(invalidPasswordAccount.password, savedAccount.password) } returns false

        assertThrows<PasswordMismatchException> {
            testService.validateAccount(
                accountId = "savedIdId",
                accountPassword = "invalidPassword",
            )
        }

        verify(exactly = 1) { accountRepository.findByIdOrNull(invalidPasswordAccount.id) }
        verify(exactly = 1) { passwordEncoder.matches(invalidPasswordAccount.password, savedAccount.password) }
    }

    @Test
    fun `엑세스 토큰 생성하기`() {
        every { tokenProvider.createToken(savedAccount.id, Token.ACCESS) } returns accessToken

        val accessToken = testService.createAccessToken(savedAccount.id)

        assertThat(accessToken).isEqualTo(this.accessToken)

        verify(exactly = 1) { tokenProvider.createToken(savedAccount.id, Token.ACCESS) }
    }

    @Test
    fun `리프레시 토큰 생성하기`() {
        every { tokenProvider.createToken(savedAccount.id, Token.REFRESH) } returns refreshToken

        val refreshToken = testService.createRefreshToken(savedAccount.id)

        assertThat(refreshToken).isEqualTo(this.refreshToken)

        verify(exactly = 1) { tokenProvider.createToken(savedAccount.id, Token.REFRESH) }
    }
}