package com.dsm.kkoribyeol.repository

import com.dsm.kkoribyeol.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String>