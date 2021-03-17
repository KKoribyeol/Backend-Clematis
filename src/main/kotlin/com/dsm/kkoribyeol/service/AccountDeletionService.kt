package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountDeletionService(
    private val accountRepository: AccountRepository,
) {

    fun deleteAccount(accountId: String) =
        accountRepository.deleteById(accountId)
}