package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.exception.AccountNotFoundException
import com.dsm.clematis.domain.account.repository.AccountRepository
import com.dsm.clematis.domain.account.exception.PasswordMismatchException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountValidationService(
    private val passwordEncoder: PasswordEncoder,
    private val accountRepository: AccountRepository,
) {

    fun validateAccount(accountId: String, accountPassword: String): Boolean {
        val findAccount = findAccountById(accountId)

        return if (passwordEncoder.matches(accountPassword, findAccount.password))
            true
        else
            throw PasswordMismatchException(accountPassword)
    }

    private fun findAccountById(accountId: String) =
        accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundException(accountId)
}