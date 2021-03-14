package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.exception.AlreadyExistAccountException
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.service.provider.AuthenticationProvider
import com.dsm.kkoribyeol.service.provider.TokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class AuthenticationProviderTest {
    private val accountRepository = mockk<AccountRepository>()
    private val tokenProvider = mockk<TokenProvider>()
    private val testService = AuthenticationService(
        accountRepository = accountRepository,
        tokenProvider = tokenProvider,
        passwordEncoder = BCryptPasswordEncoder(),
    )

    private val savedAccount = Account(
        id = "savedIdId",
        password = "savedPassword",
        name = "savedName",
    )
    private val newAccount = Account(
        id = "idIdIdId",
        password = "password",
        name = "nameName",
    )

    @Test
    fun `계정 생성하기`() {
        every { accountRepository.existsById(savedAccount.id) } returns true
        every { accountRepository.existsById(newAccount.id) } returns false
        every { accountRepository.save(any()) } returns newAccount

        val account = testService.createAccount(
            accountId = "idIdIdId",
            accountPassword = "password",
            accountName = "nameName",
        )

        assertThat(account.id).isEqualTo(newAccount.id)
        assertThat(account.password).isEqualTo(newAccount.password)
        assertThat(account.name).isEqualTo(newAccount.name)

        verify(exactly = 1) { accountRepository.existsById("idIdIdId") }
        verify(exactly = 1) { accountRepository.save(any()) }
    }

    @Test
    fun `계정 생성하기 - throw AlreadyExistAccountException`() {
        every { accountRepository.existsById(savedAccount.id) } returns true
        every { accountRepository.existsById(newAccount.id) } returns false
        every { accountRepository.save(any()) } returns newAccount

        assertThrows<AlreadyExistAccountException> {
            testService.createAccount(
                accountId = "savedIdId",
                accountPassword = "savedPassword",
                accountName = "savedName",
            )
        }

        verify(exactly = 1) { accountRepository.existsById("savedIdId") }
        verify(exactly = 0) { accountRepository.save(any()) }
    }
}