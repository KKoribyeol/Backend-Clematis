package com.dsm.clematis.domain.account.repository

import com.dsm.clematis.domain.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String>