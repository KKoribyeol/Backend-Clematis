package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.exception.TargetNotFoundException
import com.dsm.kkoribyeol.repository.TargetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TargetModificationService(
    private val targetRepository: TargetRepository,
) {

    fun modifyTarget(projectCode: String, targetToken: String, newNickname: String?, newName: String?) =
        findTarget(
            projectCode = projectCode,
            targetToken = targetToken
        ).modifyContent(
            nickname = newNickname,
            name = newName,
        )

    private fun findTarget(projectCode: String, targetToken: String) =
        targetRepository.findByProjectCodeAndToken(
            code = projectCode,
            token = targetToken,
        ) ?: throw TargetNotFoundException(targetToken)
}