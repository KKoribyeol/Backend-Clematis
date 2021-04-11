package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.account.exception.AccountNotFoundException
import com.dsm.clematis.domain.account.repository.AccountRepository
import com.dsm.clematis.domain.account.exception.PasswordMismatchException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

internal class AccountValidationServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val testService = AccountValidationService(
        accountRepository = accountRepository,
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
}