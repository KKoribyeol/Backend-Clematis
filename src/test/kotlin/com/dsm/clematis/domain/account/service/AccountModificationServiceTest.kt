package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.account.exception.AccountNotFoundException
import com.dsm.clematis.domain.account.exception.PasswordMismatchException
import com.dsm.clematis.domain.account.repository.AccountRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

internal class AccountModificationServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val testService = AccountModificationService(
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
    fun `패스워드 변경`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        testService.modifyAccountPassword(
            accountId = savedAccount.id,
            accountPassword = nonExistAccount.password,
            accountConfirmPassword = nonExistAccount.password,
        )

        verify(exactly = 1) { accountRepository.findByIdOrNull(savedAccount.id) }
        verify(exactly = 1) { passwordEncoder.encode(nonExistAccount.password) }
    }

    @Test
    fun `패스워드 변경 - throw AccountNotFoundException`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        assertThrows<AccountNotFoundException> {
            testService.modifyAccountPassword(
                accountId = nonExistAccount.id,
                accountPassword = nonExistAccount.password,
                accountConfirmPassword = nonExistAccount.password,
            )
        }

        verify(exactly = 1) { accountRepository.findByIdOrNull(nonExistAccount.id) }
        verify(exactly = 0) { passwordEncoder.encode(any()) }
    }

    @Test
    fun `패스워드 변경 - throw PasswordMismatchException`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null
        every { passwordEncoder.encode(nonExistAccount.password) } returns nonExistAccount.password

        assertThrows<PasswordMismatchException> {
            testService.modifyAccountPassword(
                accountId = savedAccount.id,
                accountPassword = savedAccount.password,
                accountConfirmPassword = nonExistAccount.password,
            )
        }

        verify(exactly = 0) { accountRepository.findByIdOrNull(any()) }
        verify(exactly = 0) { passwordEncoder.encode(any()) }
    }

    @Test
    fun `이름 변경`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null

        testService.modifyAccountName(
            accountId = savedAccount.id,
            accountName = nonExistAccount.name,
        )

        verify(exactly = 1) { accountRepository.findByIdOrNull(savedAccount.id) }
    }

    @Test
    fun `이름 변경 - throw AccountNotFoundException`() {
        every { accountRepository.findByIdOrNull(savedAccount.id) } returns savedAccount
        every { accountRepository.findByIdOrNull(nonExistAccount.id) } returns null

        assertThrows<AccountNotFoundException> {
            testService.modifyAccountName(
                accountId = nonExistAccount.id,
                accountName = nonExistAccount.name,
            )
        }

        verify(exactly = 1) { accountRepository.findByIdOrNull(nonExistAccount.id) }
    }
}