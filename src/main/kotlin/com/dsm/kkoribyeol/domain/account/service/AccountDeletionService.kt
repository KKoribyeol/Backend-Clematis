package com.dsm.kkoribyeol.domain.account.service

import com.dsm.kkoribyeol.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountDeletionService(
    private val accountRepository: AccountRepository,
) {

    fun deleteAccount(accountId: String) =
        accountRepository.deleteById(accountId)
}