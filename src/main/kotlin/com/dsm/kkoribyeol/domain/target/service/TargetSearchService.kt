package com.dsm.kkoribyeol.domain.target.service

import com.dsm.kkoribyeol.domain.target.domain.Target
import com.dsm.kkoribyeol.domain.target.exception.TargetNotFoundException
import com.dsm.kkoribyeol.domain.target.repository.TargetRepository
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