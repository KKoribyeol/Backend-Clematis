package com.dsm.kkoribyeol.service

import com.dsm.kkoribyeol.controller.request.TargetRegistrationAllRequest.TargetRegistrationRequest
import com.dsm.kkoribyeol.domain.Target
import com.dsm.kkoribyeol.exception.AlreadyExistTargetException
import com.dsm.kkoribyeol.exception.ProjectNotFoundException
import com.dsm.kkoribyeol.repository.ProjectRepository
import com.dsm.kkoribyeol.repository.TargetRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TargetRegistrationService(
    private val targetRepository: TargetRepository,
    private val projectRepository: ProjectRepository,
) {

    fun registerTarget(projectCode: String, targets: List<TargetRegistrationRequest>) {
        val isAlreadyExist = isExistTarget(
            projectCode = projectCode,
            tokens = targets.map { it.targetToken }
        )

        if (isAlreadyExist)
            throw AlreadyExistTargetException()
        else
            save(projectCode, targets)
    }

    private fun isExistTarget(projectCode: String, tokens: List<String>) =
        targetRepository.existsByProjectCodeAndTokenIn(projectCode, tokens)

    private fun save(projectCode: String, targets: List<TargetRegistrationRequest>) {
        targetRepository.saveAll(
            targets.map {
                Target(
                    token = it.targetToken,
                    nickname = it.targetNickname,
                    name = it.targetName,
                    project = findProjectByCode(projectCode),
                )
            }
        )
    }

    @Transactional
    fun unregisterTarget(projectCode: String, targets: List<String>) =
        targetRepository.deleteByProjectCodeAndTokenIn(
            code = projectCode,
            tokens = targets,
        )

    private fun findProjectByCode(projectCode: String) =
        projectRepository.findByIdOrNull(projectCode) ?: throw ProjectNotFoundException(projectCode)
}