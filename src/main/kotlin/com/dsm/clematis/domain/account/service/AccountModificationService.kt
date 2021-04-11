package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.exception.AccountNotFoundException
import com.dsm.clematis.domain.account.exception.PasswordMismatchException
import com.dsm.clematis.domain.account.repository.AccountRepository
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

    fun modifyAccountPassword(accountId: String, accountPassword: String, accountConfirmPassword: String) =
        if (accountPassword == accountConfirmPassword)
            findAccountByCode(accountId).modifyPassword(passwordEncoder.encode(accountPassword))
        else
            throw PasswordMismatchException(accountPassword, accountConfirmPassword)

    fun modifyAccountName(accountId: String, accountName: String) =
        findAccountByCode(accountId).modifyName(accountName)

    private fun findAccountByCode(accountId: String) =
        accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundException(accountId)
}