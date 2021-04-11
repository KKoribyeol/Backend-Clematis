package com.dsm.clematis.domain.account.repository

import com.dsm.clematis.domain.account.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RedisRepository : CrudRepository<RefreshToken, String>