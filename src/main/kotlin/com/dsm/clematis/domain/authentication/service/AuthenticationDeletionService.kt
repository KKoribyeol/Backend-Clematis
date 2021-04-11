package com.dsm.clematis.domain.authentication.service

import com.dsm.clematis.domain.authentication.repository.RedisRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationDeletionService(
    private val redisRepository: RedisRepository,
) {

    @Transactional
    fun deleteToken(
        accountId: String,
    ) = redisRepository.deleteById(accountId)
}