package com.dsm.kkoribyeol.domain.account.service

import com.dsm.kkoribyeol.domain.account.domain.Account
import com.dsm.kkoribyeol.domain.account.exception.AccountNotFoundException
import com.dsm.kkoribyeol.domain.account.repository.AccountRepository
import com.dsm.kkoribyeol.domain.account.service.AccountDeletionService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AccountDeletionServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val testService = AccountDeletionService(
        accountRepository = accountRepository,
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
    fun `계정 삭제하기`() {
        every { accountRepository.deleteById(savedAccount.id) } just Runs
        every { accountRepository.deleteById(nonExistAccount.id) } throws AccountNotFoundException(nonExistAccount.id)

        testService.deleteAccount("savedIdId")

        verify(exactly = 1) { accountRepository.deleteById(savedAccount.id) }
    }

    @Test
    fun `계정 삭제하기 - throw AccountNotFoundException`() {
        every { accountRepository.deleteById(savedAccount.id) } just Runs
        every { accountRepository.deleteById(nonExistAccount.id) } throws AccountNotFoundException(nonExistAccount.id)

        assertThrows<AccountNotFoundException> {
            testService.deleteAccount("idIdIdId")
        }

        verify(exactly = 1) { accountRepository.deleteById(nonExistAccount.id) }
    }
}