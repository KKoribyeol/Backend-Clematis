package com.dsm.kkoribyeol.domain.account.repository

import com.dsm.kkoribyeol.domain.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String>