package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.exception.AlreadyExistAccountException
import com.dsm.kkoribyeol.repository.AccountRepository
import com.dsm.kkoribyeol.service.provider.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val accountRepository: AccountRepository,
    private val tokenProvider: TokenProvider,
) : UserDetailsService {

    fun createAccount(accountId: String, accountPassword: String, accountName: String) =
        if (isExistAccount(accountId))
            throw AlreadyExistAccountException(accountId)
        else
            saveAccount(accountId, accountPassword, accountName)

    private fun isExistAccount(accountId: String) =
        accountRepository.existsById(accountId)

    private fun saveAccount(accountId: String, accountPassword: String, accountName: String) =
        accountRepository.save(
            Account(
                id = accountId,
                password = accountPassword,
                name = accountName,
            )
        )

    override fun loadUserByUsername(username: String) =
        accountRepository.findByIdOrNull(username) ?: throw AccountNotFoundException(username)
}