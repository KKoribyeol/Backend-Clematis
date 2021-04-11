package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.account.exception.AlreadyExistAccountException
import com.dsm.clematis.domain.account.repository.AccountRepository
import com.dsm.clematis.global.security.provider.TokenProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

internal class AccountCreationServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val testService = AccountCreationService(
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

    @Test
    fun `계정 생성하기`() {
        every { accountRepository.existsById(savedAccount.id) } returns true
        every { accountRepository.existsById(nonExistAccount.id) } returns false
        every { accountRepository.save(any()) } returns nonExistAccount
        every { passwordEncoder.encode(savedAccount.password) } returns savedAccount.password
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        testService.createAccount(
            accountId = "idIdIdId",
            accountPassword = "password",
            accountName = "nameName",
        )

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
}