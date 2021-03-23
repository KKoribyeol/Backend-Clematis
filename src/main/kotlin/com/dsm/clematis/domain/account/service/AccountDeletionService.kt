package com.dsm.clematis.domain.account.service

import com.dsm.clematis.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountDeletionService(
    private val accountRepository: AccountRepository,
) {

    fun deleteAccount(accountId: String) =
        accountRepository.deleteById(accountId)
}