package com.dsm.clematis.domain.account.repository

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class AccountRepositoryTest(
    private val accountRepository: AccountRepository,
)