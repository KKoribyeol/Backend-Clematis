package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.exception.PasswordMismatchException
import com.dsm.kkoribyeol.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.password.PasswordEncoder

@Service
@Transactional
class AccountModificationService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun modifyPassword(accountId: String, accountPassword: String, accountConfirmPassword: String) =
        if (accountPassword == accountConfirmPassword)
            findAccountById(accountId).modifyPassword(passwordEncoder.encode(accountPassword))
        else
            throw PasswordMismatchException(accountPassword, accountConfirmPassword)

    fun modifyName(accountId: String, accountName: String) =
        findAccountById(accountId).modifyName(accountName)

    private fun findAccountById(accountId: String) =
        accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundException(accountId)
}