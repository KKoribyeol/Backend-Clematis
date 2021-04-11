package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.domain.Account
import com.dsm.clematis.domain.account.exception.AlreadyExistAccountException
import com.dsm.clematis.domain.account.repository.AccountRepository
import com.dsm.clematis.global.security.provider.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountCreationService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun createAccount(accountId: String, accountPassword: String, accountName: String) {
        if (isExistAccount(accountId))
            throw AlreadyExistAccountException(accountId)
        else
            saveAccount(accountId, accountPassword, accountName)
    }

    private fun isExistAccount(accountId: String) =
        accountRepository.existsById(accountId)

    private fun saveAccount(accountId: String, accountPassword: String, accountName: String) =
        accountRepository.save(
            Account(
                id = accountId,
                password = passwordEncoder.encode(accountPassword),
                name = accountName,
            )
        )
}