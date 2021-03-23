package com.dsm.kkoribyeol.domain.account.repository

import com.dsm.kkoribyeol.domain.account.repository.AccountRepository
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class AccountRepositoryTest(
    private val accountRepository: AccountRepository,
)