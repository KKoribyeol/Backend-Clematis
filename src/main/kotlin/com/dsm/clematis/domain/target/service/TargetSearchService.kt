package com.dsm.clematis.domain.target.service

import com.dsm.clematis.domain.target.domain.Target
import com.dsm.clematis.domain.target.exception.TargetNotFoundException
import com.dsm.clematis.domain.target.repository.TargetRepository
import org.springframework.stereotype.Service

@Service
class TargetSearchService(
    private val targetRepository: TargetRepository,
) {

    fun searchAllTarget(): List<Target> = targetRepository.findAll()

    fun searchTarget(projectCode: String, targetToken: String) =
        targetRepository.findByProjectCodeAndToken(
            code = projectCode,
            token = targetToken,
        ) ?: throw TargetNotFoundException(targetToken)
}