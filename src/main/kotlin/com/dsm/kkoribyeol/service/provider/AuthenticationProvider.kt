package com.dsm.kkoribyeol.service.provider

import com.dsm.kkoribyeol.domain.Account
import com.dsm.kkoribyeol.exception.AccountNotFoundException
import com.dsm.kkoribyeol.repository.AccountRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthenticationProvider(
    private val accountRepository: AccountRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String) =
        accountRepository.findByIdOrNull(username) ?: throw AccountNotFoundException(username)

    fun getAccountIdByAuthentication() =
        (SecurityContextHolder.getContext().authentication.principal as Account).id
}