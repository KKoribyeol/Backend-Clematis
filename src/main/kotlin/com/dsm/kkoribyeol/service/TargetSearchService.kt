package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.domain.Target
import com.dsm.kkoribyeol.exception.TargetNotFoundException
import com.dsm.kkoribyeol.repository.TargetRepository
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